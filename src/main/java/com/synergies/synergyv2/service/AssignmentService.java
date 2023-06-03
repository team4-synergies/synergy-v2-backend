package com.synergies.synergyv2.service;

import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.config.S3.FileService;
import com.synergies.synergyv2.model.dto.AssignmentRequestDto;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.model.entity.AssignmentEntity;
import com.synergies.synergyv2.repository.AssignmentRepository;
import com.synergies.synergyv2.repository.AssignmentSubmitRepository;
import com.synergies.synergyv2.repository.UserRepository;
import com.synergies.synergyv2.repository.mapping.AssignmentMapping;
import com.synergies.synergyv2.repository.mapping.SubmitMapping;
import com.synergies.synergyv2.repository.mapping.UserMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final FileService fileService;
    private final AssignmentSubmitRepository submitRepository;
    private final UserRepository userRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd_hhmmss");

    // 과제 등록
    @Transactional
    public void createAssignment(AssignmentRequestDto assignment, MultipartFile file) {

        String fileName = "";

        if(assignment.getContent() == null)
            assignment.setContent("");

        if(assignment.getFile() != null) {
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
        List<AssignmentMapping> assignments = assignmentRepository.findAllProjectedBy();
        List<AssignmentResponseDto.AssignmentList> assignList = new ArrayList<>();
        for(AssignmentMapping assign : assignments) {
            assignList.add(AssignmentResponseDto.AssignmentList.builder()
                                                .id(assign.getId())
                                                .title(assign.getTitle())
                                                .regDate((assign.getRegDate()).toString())
                                                .build());
        }
        return assignList;
    }

    // 과제 상세 데이터
    public AssignmentResponseDto.AssignmentDetail getAssignment(int id) {
        AssignmentEntity assignEntity = assignmentRepository.findById(id)
                                        .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));

        AssignmentResponseDto.AssignmentDetail assignment = assignEntity.toDto();
        assignment.setAssignmentFile(fileService.getUrl()+"/admin/"+assignment.getAssignmentFile());
        return assignment;
    }

    // 과제 제출 현황 리스트
    public AssignmentResponseDto.AssignmentSubmitList getSubmitList(int id) {
        List<UserMapping> students = userRepository.findAllProjectedBy();
        List<SubmitMapping> submit = submitRepository.findSubmitStudents(id);
        List<AssignmentResponseDto.SubmitList> submitList = new ArrayList<>();
        List<String> unSubmitList = new ArrayList<>();

        // 미제출 학생 리스트
        Map<Integer, String> submitMap = new HashMap<>();   // 제출한 학생 Map
        for(SubmitMapping data : submit) {
            submitMap.put(data.getUserId(), data.getNickname());
        }

        for(UserMapping data : students) {          // submitMap에 학생 ID가 없을 시 추가
            if(!submitMap.containsKey(data.getId())) {
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
        LocalDate Date = LocalDate.now();
        LocalTime time = LocalTime.of(0, 0);
        LocalDateTime dateTime = LocalDateTime.of(Date, time);

        return assignmentRepository.countByUpdateDateAfter(dateTime);
    }
}
