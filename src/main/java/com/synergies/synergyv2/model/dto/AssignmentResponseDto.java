package com.synergies.synergyv2.model.dto;

import lombok.*;

import java.util.List;

public class AssignmentResponseDto {

    @Getter
    @Builder
    public static class AssignmentList {
        private int id;
        private String title;
        private String regDate;
    }

    @Getter
    @Setter
    @Builder
    public static class AssignmentDetail {
        private int id;
        private String title;
        private String content;
        private String assignmentFile;
        private String regDate;
        private String updateDate;
    }

    @Getter
    @Setter
    @Builder
    public static class SubmitDetail {
        private int id;
        private String studentName;
        private String submitFile;
        private int submitCount;
        private String regDate;
        private String updateDate;

        // 코멘트 리스트 추가
    }

    @Getter
    @Builder
    public static class SubmitList {
        private int submitId;
        private String studentName;
        private String updateDate;
    }

    @Getter
    @Builder
    public static class AssignmentSubmitList {
        private List<SubmitList> submitList;
        private List<String> unSubmitList;

    }

    @Getter
    @Builder
    public static class CommentList {
        private int id;
        private String content;
        private String regDate;
    }
}
