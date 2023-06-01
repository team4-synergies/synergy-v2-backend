package com.synergies.synergyv2.model.entity;

import com.synergies.synergyv2.common.BaseTime;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name="ASSIGNMENTS")
public class AssignmentEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    private String assignmentFile;

    public AssignmentResponseDto.AssignmentDetail toDto() {
        return AssignmentResponseDto.AssignmentDetail.builder()
                .id(id)
                .title(title)
                .content(content)
                .assignmentFile(assignmentFile)
                .regDate(getRegDate().toString())
                .updateDate(getUpdateDate().toString())
                .build();
    }

    public AssignmentEntity updateAssignment(String title, String content, String file) {
        this.title = title;
        this.content = content;
        this.assignmentFile = file;
        return this;
    }
}
