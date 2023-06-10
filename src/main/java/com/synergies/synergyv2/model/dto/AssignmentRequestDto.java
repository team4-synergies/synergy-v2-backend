package com.synergies.synergyv2.model.dto;

import com.synergies.synergyv2.model.entity.AssignmentEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class AssignmentRequestDto {
    private String title;
    private String content;

    public AssignmentEntity toEntity(String fileName) {
        return AssignmentEntity.builder()
                .title(title)
                .content(content)
                .assignmentFile(fileName)
                .build();
    }
}