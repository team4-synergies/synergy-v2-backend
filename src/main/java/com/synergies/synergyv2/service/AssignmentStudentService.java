package com.synergies.synergyv2.service;

import com.synergies.synergyv2.auth.Role;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.config.S3.FileService;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.model.entity.AssignmentEntity;
import com.synergies.synergyv2.model.entity.AssignmentSubmitEntity;
import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.AssignmentRepository;
import com.synergies.synergyv2.repository.AssignmentSubmitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignmentStudentService {

    private final AssignmentSubmitRepository submitRepository;
    private final AssignmentRepository assignmentRepository;
    private final FileService fileService;
    
    // 학생 과제 제출
    @Transactional
    public void createSubmit(int id, MultipartFile file) {

        // 유저 데이터 가져오기 (쿠키 생성 시 제거할 것)
        UserEntity user = new UserEntity(UUID.randomUUID(), "12345678", "nickname", "email", Role.STUDENT);

        // 과제 데이터 가져오기
        AssignmentEntity assignment = assignmentRepository.findById(id)
                                    .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));

        // 파일명
        String fileName = assignment.getAssignmentFile()+"/"+UUID.randomUUID();
        fileService.uploadFile(fileName, false, file);

        AssignmentSubmitEntity submitEntity = AssignmentSubmitEntity.builder()
                                                .user(user)
                                                .assignment(assignment)
                                                .submitFile(fileName)
                                                .submitCount(1)
                                                .build();

        submitRepository.save(submitEntity);
    }

    // 학생 과제 재제출
    @Transactional
    public void updateSubmit(int id, MultipartFile file) {
        AssignmentSubmitEntity submitEntity = submitRepository.findById(id)
                                                 .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));
        fileService.uploadFile(submitEntity.getSubmitFile(), false, file);
        submitEntity.updateSubmit(submitEntity.getSubmitCount()+1);

    }

    // 학생이 제출한 과제 데이터
    public AssignmentResponseDto.SubmitDetail getSubmit(int id) {
        AssignmentSubmitEntity submitEntity = submitRepository.findById(id)
                                                .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));
        AssignmentResponseDto.SubmitDetail submitDetail = submitEntity.toDto();
        submitDetail.setSubmitFile(fileService.getUrl()+"/student/"+submitDetail.getSubmitFile());
        return submitDetail;
    }
}
