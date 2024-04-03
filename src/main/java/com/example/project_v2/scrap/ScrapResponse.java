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
        private Integer scrapedId; // 공고 번호 또는 이력서 번호
        private String username; // 공고 또는 이력서의 주인 이름
        private String deadline; // 공고 마감일
        private String title; // 공고 제목 또는 이력서 제목
        private String type; // 고용 형태

        public ScrapListDTO(Scrap scrap, User sessionUser) {
            this.id = scrap.getId();
            this.userId = sessionUser.getId();
            if(sessionUser.getRole() == 0){
                this.scrapedId = scrap.getNotice().getId();
                this.username = scrap.getNotice().getUser().getUsername();
                this.deadline = scrap.getNotice().getDeadline();
                this.title = scrap.getNotice().getTitle();
                this.type = scrap.getNotice().getType();
            }else{
                this.scrapedId = scrap.getResume().getId();
                this.username = scrap.getResume().getUser().getUsername();
                this.deadline = "";
                this.title = scrap.getResume().getTitle();
                this.type = "";
            }
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