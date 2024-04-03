package com.example.project_v2.scrap;

import com.example.project_v2.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

public class ScrapResponse {

    @Data
    public static class ScrapListDTO{
        private Integer id; // 스크랩 번호
        private Integer userId; // 스크랩 유저 번호
        private Integer noticeId; // 공고 주인 번호
        private String noticeUsername; // 공고 주인 이름
        private String deadline; // 공고 마감일
        private String title; // 공고 제목
        private String type; // 고용 형태

        public ScrapListDTO(Scrap scrap, Integer sessionUserId) {
            this.id = scrap.getId();
            this.userId = sessionUserId;
            this.noticeId = scrap.getNotice().getId();
            this.noticeUsername = scrap.getNotice().getUser().getUsername();
            this.deadline = scrap.getNotice().getDeadline();
            this.title = scrap.getNotice().getTitle();
            this.type = scrap.getNotice().getType();
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL) // null이 뜨는 json은 출력안하게 하는 어노테이션
    public static class DTO {
        private Integer id;
        private Integer userId;
        private Integer resumeId;
        private Integer noticeId;
        private Timestamp createdAt;

        public DTO(Scrap scrap) {
            this.id = scrap.getId();
            this.userId = scrap.getUser().getId();

            if (scrap.getUser().getRole() == 1) {
                this.resumeId = scrap.getResume().getId();
            } else {
                this.noticeId = scrap.getNotice().getId();
            }
            this.createdAt = scrap.getCreatedAt();
        }
    }
}