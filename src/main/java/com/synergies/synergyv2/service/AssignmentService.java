package com.synergies.synergyv2.service;

import com.synergies.synergyv2.auth.Role;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.config.S3.FileService;
import com.synergies.synergyv2.model.dto.AssignmentRequestDto;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.model.entity.AssignmentEntity;
import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.AssignmentRepository;
import com.synergies.synergyv2.repository.AssignmentSubmitRepository;
import com.synergies.synergyv2.repository.UserRepository;
import com.synergies.synergyv2.repository.mapping.AssignmentMapping;
import com.synergies.synergyv2.repository.mapping.SubmitMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final FileService fileService;
    private final AssignmentSubmitRepository submitRepository;
    private final UserRepository userRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd_hhmmss");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm");
    private LocalTime time = LocalTime.of(0, 0);


    // 과제 등록
    @Transactional
    public void createAssignment(AssignmentRequestDto assignment, MultipartFile file) {

        String fileName = "";

        if(assignment.getContent() == null)
            assignment.setContent("");

        if(file != null) {
            Date nowDate = new Date();
            fileName = simpleDateFormat.format(nowDate);
            fileService.uploadFile(fileName, true, file);
        }

        AssignmentEntity assignEntity = assignment.toEntity(fileName);
        assignmentRepository.save(assignEntity);
    }

    // 과제 수정
    @Transactional
    public void updateAssignment(int id, AssignmentRequestDto assignment, MultipartFile file) {
        Date nowDate = new Date();
        AssignmentEntity updateAssign = assignmentRepository.findById(id)
                                        .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));

        if(file == null) {
            updateAssign.updateAssignment(assignment.getTitle(), assignment.getContent(), updateAssign.getAssignmentFile());
        } else {
            String fileName = simpleDateFormat.format(nowDate);
            fileService.deleteFile(true, updateAssign.getAssignmentFile());
            fileService.uploadFile(fileName, true, file);
            updateAssign.updateAssignment(assignment.getTitle(), assignment.getContent(), fileName);
        }
    }

    // 과제 삭제
    @Transactional
    public void deleteAssignment(int id) {
        AssignmentEntity deleteAssign = assignmentRepository.findById(id)
                .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));

        fileService.deleteFile(true, deleteAssign.getAssignmentFile());
        assignmentRepository.deleteById(id);
    }

    // 과제 리스트
    public List<AssignmentResponseDto.AssignmentList> getAssignmentList() {
        List<AssignmentMapping> assignments = assignmentRepository.findAllProjectedByOrderByRegDateDesc();
        List<AssignmentResponseDto.AssignmentList> assignList = new ArrayList<>();
        for(AssignmentMapping assign : assignments) {
            assignList.add(AssignmentResponseDto.AssignmentList.builder()
                                                .id(assign.getId())
                                                .title(assign.getTitle())
                                                .updateDate(assign.getUpdateDate().toString())
                                                .build());
        }
        return assignList;
    }

    // 과제 상세 데이터
    public AssignmentResponseDto.AssignmentDetail getAssignment(int id) {
        AssignmentEntity assignEntity = assignmentRepository.findById(id)
                                        .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));
        String regDate = assignEntity.getRegDate().format(formatter).toString();
        String updateDate = assignEntity.getUpdateDate().format(formatter).toString();
        AssignmentResponseDto.AssignmentDetail assignment = assignEntity.toDto();
        assignment.setRegDate(regDate);
        assignment.setUpdateDate(updateDate);

        if(!assignment.getAssignmentFile().equals("")) {
            assignment.setAssignmentFile(fileService.getUrl()+"/admin/"+assignment.getAssignmentFile());
        }
        return assignment;
    }

    // 과제 제출 현황 리스트
    public AssignmentResponseDto.AssignmentSubmitList getSubmitList(int id) {
        List<UserEntity> students = userRepository.findByRole(Role.STUDENT);
        List<SubmitMapping> submit = submitRepository.findSubmitStudents(id);
        List<AssignmentResponseDto.SubmitList> submitList = new ArrayList<>();
        List<String> unSubmitList = new ArrayList<>();

        // 미제출 학생 리스트
        Map<UUID, String> submitMap = new HashMap<>();   // 제출한 학생 Map
        for(SubmitMapping data : submit) {
            submitMap.put(data.getUserId(), data.getNickname());
        }

        for(UserEntity data : students) {          // submitMap에 학생 ID가 없을 시 추가
            if(!submitMap.containsKey(data.toCustomUserDetails().getUserId())) {
                unSubmitList.add(data.getName());
            }
        }

        // 제출 학생 리스트
        for(SubmitMapping data : submit) {
            submitList.add(AssignmentResponseDto.SubmitList.builder()
                            .submitId(data.getId())
                            .studentName(data.getNickname())
                            .updateDate(data.getUpdateDate().toString())
                            .build());
        }

        return AssignmentResponseDto.AssignmentSubmitList.builder()
                .submitList(submitList)
                .unSubmitList(unSubmitList)
                .build();

    }

    // 오늘 등록한 과제 개수
    public int getTodayCount() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), time);
        return assignmentRepository.countByUpdateDateAfter(dateTime);
    }

    // 오늘 등록한 과제 리스트
    public List<AssignmentResponseDto.AssignmentDetail> getTodayAssignment() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), time);
        List<AssignmentEntity> assignments = assignmentRepository.findByUpdateDateAfter(dateTime);
        System.out.println("today : " + LocalDateTime.now());
        List<AssignmentResponseDto.AssignmentDetail> assignmentList = new ArrayList<>();
        for(AssignmentEntity assign : assignments) {
            String regDate = assign.getRegDate().format(formatter).toString();
            String updateDate = assign.getUpdateDate().format(formatter).toString();

            assignmentList.add(AssignmentResponseDto.AssignmentDetail.builder()
                                .id(assign.getId())
                                .title(assign.getTitle())
                                .content(assign.getContent())
                                .assignmentFile(assign.getAssignmentFile())
                                .regDate(regDate)
                                .updateDate(updateDate)
                                .build());
        }
        return assignmentList;
    }
}
