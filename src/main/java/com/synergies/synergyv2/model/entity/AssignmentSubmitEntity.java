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
@Table(name="ASSIGNMENT_SUBMIT")
public class AssignmentSubmitEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 유저 엔티티 생성시 추가
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USER_ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ASSIGNMENT_ID")
    private AssignmentEntity assignment;

    private String submitFile;

    private int submitCount;


    public AssignmentSubmitEntity updateSubmit(int submitCount) {
        this.submitCount = submitCount;
        return this;
    }

    public AssignmentResponseDto.SubmitDetail toDto() {
        return AssignmentResponseDto.SubmitDetail.builder()
                .id(id)
                .studentName(user.getUserNickname())
                .submitFile(submitFile)
                .submitCount(submitCount)
                .regDate(getRegDate().toString())
                .updateDate(getUpdateDate().toString())
                .build();
    }

}
