package com.synergies.synergyv2.model.dto;

import com.synergies.synergyv2.model.entity.AssignmentEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AssignmentRequestDto {

    @Getter
    @Setter
    @Builder
    public static class AssignmentRegister {
        private String title;
        private String content;
        private MultipartFile file;

        public AssignmentEntity toEntity(String fileName) {
            return AssignmentEntity.builder()
                    .title(title)
                    .content(content)
                    .assignmentFile(fileName)
                    .build();
        }
    }





}
