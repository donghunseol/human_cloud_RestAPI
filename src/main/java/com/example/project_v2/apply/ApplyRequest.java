package com.example.project_v2.apply;

import lombok.Data;

public class ApplyRequest {

    @Data
    public static class SaveDTO{
        private Integer resumeId;
        private Integer noticeId;
    }

    @Data
    public static class CompanyListDTO{
        private Integer userId; // 유저 번호
        private Integer noticeUserId; // 공고 유저 번호
        private Integer noticeId; // 공고 번호
        private Integer resumeId; // 이력서 번호
        private Integer applyId; // 지원 번호

        private String name; // 회사 이름
        private String title; // 공고 타이틀
        private String type; // 고용 형태
        private String deadline; // 마감 형태
    }

    @Data
    public static class UserListDTO{
        private Integer userId; // 유저 번호
        private Integer noticeUserId; // 이력서 유저 번호
        private Integer noticeId; // 공고 번호
        private Integer resumeId; // 이력서 번호
        private Integer applyId; // 지원 번호

        private String name; // 회사 이름
        private String title; // 공고 타이틀
        private String type; // 고용 형태
        private String deadline; // 마감 형태
    }
}
