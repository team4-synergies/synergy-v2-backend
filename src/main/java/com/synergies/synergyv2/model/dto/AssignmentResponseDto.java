package com.synergies.synergyv2.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class AssignmentResponseDto {

    @Getter
    @Setter
    @Builder
    public static class AssignmentList {
        private int id;
        private String title;
        private String regDate;
    }

}
