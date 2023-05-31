package com.synergies.synergyv2.service;

import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.config.S3.FileService;
import com.synergies.synergyv2.model.dto.AssignmentRequestDto;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.model.entity.AssignmentEntity;
import com.synergies.synergyv2.repository.mapping.AssignmentMapping;
import com.synergies.synergyv2.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final FileService fileService;

    //bucketName
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmm");

    // 과제 등록
    @Transactional
    public void createAssignment(AssignmentRequestDto.AssignmentRegister assignment, MultipartFile file) {
        Date nowDate = new Date();
        String fileName = simpleDateFormat.format(nowDate);
        fileService.uploadFile(fileName, true, file);
        AssignmentEntity assignEntity = assignment.toEntity(fileName);
        assignmentRepository.save(assignEntity);
    }

    // 과제 수정
    @Transactional
    public void updateAssignment(int id, AssignmentRequestDto.AssignmentRegister assignment, MultipartFile file) {
        Date nowDate = new Date();
        AssignmentEntity beforeAssign = assignmentRepository.findById(id)
                                        .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));

        AssignmentEntity assignEntity;
        if(file == null) {
            assignEntity = AssignmentEntity.builder()
                            .id(id)
                            .title(assignment.getTitle())
                            .content(assignment.getContent())
                            .assignmentFile(beforeAssign.getAssignmentFile())
                            .build();
        } else {
            String fileName = simpleDateFormat.format(nowDate);
            fileService.deleteFile(true, beforeAssign.getAssignmentFile());
            fileService.uploadFile(fileName, true, file);
            System.out.println("fileName : " + fileName);

            assignEntity = AssignmentEntity.builder()
                            .id(id)
                            .title(assignment.getTitle())
                            .content(assignment.getContent())
                            .assignmentFile(fileName)
                            .build();
        }

        assignmentRepository.save(assignEntity);
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
            System.out.println("***" + assign.getRegDate());
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
        Optional<AssignmentEntity> assignEntity = assignmentRepository.findById(id);
        AssignmentResponseDto.AssignmentDetail assignment = assignEntity.get().toDto();
        return assignment;
    }
}
