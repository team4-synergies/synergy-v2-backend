package com.synergies.synergyv2.model.entity;

import com.synergies.synergyv2.common.BaseTime;
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

    public AssignmentEntity(String title, String content, String file) {
        this.title = title;
        this.content = content;
        this.assignmentFile = file;
    }
}
