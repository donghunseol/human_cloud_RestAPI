package com.example.project_v2.apply;

import com.example.project_v2.notice.Notice;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.user.User;
import lombok.Data;

import java.sql.Timestamp;

public class ApplyResponse {

    @Data
    public static class UserListDTO{
        private Integer id; // 유저 번호
        private Integer resumeUserId; // 이력서 유저 번호
        private Integer noticeUserId; // 공고 유저 번호
        private Integer applyId; // 지원 번호
        private Integer resumeId; // 이력서 번호
        private Integer noticeId;
        private String name; // 공고 올린 회사 이름
        private String title; // 공고 타이틀
        private String deadLine; // 마감일
        private String type;
        private boolean pass;


        public UserListDTO(Apply apply) {
            this.id = apply.getUser().getId();
            this.resumeUserId = apply.getResume().getUser().getId();
            this.noticeUserId = apply.getNotice().getUser().getId();
            this.applyId = apply.getId();
            this.resumeId = apply.getNotice().getId();
            this.noticeId = apply.getNotice().getId();
            this.name = apply.getUser().getName();
            this.title = apply.getNotice().getTitle();
            this.deadLine = apply.getNotice().getDeadline();
            this.type = apply.getNotice().getType();
            this.pass = apply.getPass();
        }
    }

    @Data
    public static class DTO{
        private Integer id;
        private Integer userId;
        private Integer noticeId;
        private Integer resumeId;
        private Boolean pass;
        private Timestamp createdAt;

        public DTO(Apply apply) {
            this.id = apply.getId();
            this.userId = apply.getUser().getId();
            this.noticeId = apply.getNotice().getId();
            this.resumeId = apply.getResume().getId();
            this.pass = apply.getPass();
            this.createdAt = apply.getCreatedAt();
        }
    }

    @Data
    public static class ApplyDTO{

        private Integer id; // 지원 번호
        private Integer noticeId; // 공고 번호
        private Integer resumeId; // 이력서 번호
        private Integer noticeUserId; // 공고 유저의 번호
        private Integer resumeUserId; // 이력서의 유저의 번호
        private String name; // 공고 올린 회사 이름
        private String title; // 공고 타이틀
        private String deadline; // 마감일
        private String type; // 고용 형태
        private Boolean isApplyOwner; // 지원의 주인 여부

        public ApplyDTO(Apply apply, Notice notice, Resume resume , User sessionUser) {
            this.id = apply.getId();
            this.noticeId = notice.getId();
            this.resumeId = resume.getId();
            this.noticeUserId = notice.getUser().getId();
            this.resumeUserId = resume.getUser().getId();
            this.name = resume.getUser().getName();
            this.title = resume.getTitle();
            this.deadline = notice.getDeadline();
            this.type = notice.getType();
            this.isApplyOwner = false;
            if (sessionUser != null) {
                if (sessionUser.getId() == resumeUserId) {
                    isApplyOwner = true;
                }
            }

        }
    }

    @Data
    public static class SelectResumeDTO{
        private Integer id; // 지원 번호
        private Integer resumeId; // 이력서 번호
        private Boolean isResumeOwner; // 이력서의 주인

        public SelectResumeDTO(Apply apply, User sessionUser) {
            this.id = apply.getId();
            // 가정: Apply 객체가 Resume 객체에 대한 참조를 가지고 있다고 가정
            this.resumeId = apply.getResume().getId(); // 수정된 부분
            this.isResumeOwner = false;
            if (sessionUser != null && sessionUser.getId().equals(this.resumeId)) { // 객체 비교 방식 수정
                isResumeOwner = true;
            }
        }
    }

}
