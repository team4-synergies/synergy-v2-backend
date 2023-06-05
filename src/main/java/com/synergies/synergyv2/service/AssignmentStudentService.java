package com.synergies.synergyv2.service;

import com.synergies.synergyv2.common.response.code.AuthErrorCode;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.AuthException;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.config.S3.FileService;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.model.entity.AssignmentEntity;
import com.synergies.synergyv2.model.entity.AssignmentSubmitEntity;
import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.AssignmentRepository;
import com.synergies.synergyv2.repository.AssignmentSubmitRepository;
import com.synergies.synergyv2.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final FileService fileService;
    
    // 학생 과제 제출
    @Transactional
    public void createSubmit(UUID userId, int id, MultipartFile file) {

        // 유저 데이터 가져오기
        UserEntity user = userRepository.findById(userId)
                        .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_EXIST_USER));

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
    public void updateSubmit(UUID userId, int id, MultipartFile file) {
        AssignmentSubmitEntity submitEntity = submitRepository.findById(id)
                                                 .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));
        // 과제 제출자와 유저가 동일한지 확인
        if(userId != submitEntity.getUser().toCustomUserDetails().getUserId()) {
            throw new AuthException(AuthErrorCode.NO_PERMISSION);
        }

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
