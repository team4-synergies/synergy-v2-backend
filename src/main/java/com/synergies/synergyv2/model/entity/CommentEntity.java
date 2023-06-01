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
@Table(name="COMMENTS")
public class CommentEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSIGNMENT_SUBMIT_ID")
    private AssignmentSubmitEntity assignmentSubmit;

    private String content;
}
