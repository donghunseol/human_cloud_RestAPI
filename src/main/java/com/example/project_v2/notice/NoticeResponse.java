package com.example.project_v2.notice;

import com.example.project_v2.skill.Skill;
import com.example.project_v2.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class NoticeResponse {

    @Data
    public static class DTO{
        private Integer id;
        private Integer userId;
        private String title;
        private String type;
        private String field;
        private String workPlace;
        private String content;
        private String deadline;

        private List<SkillDTO> skills = new ArrayList<>();

        public DTO(Notice notice, User sessionUser) {
            this.id = notice.getId();
            this.userId = sessionUser.getId();
            this.title = notice.getTitle();
            this.type = notice.getType();
            this.field = notice.getField();
            this.workPlace = notice.getWorkPlace();
            this.content = notice.getContent();
            this.deadline = notice.getDeadline();

            this.skills =  notice.getSkills().stream().map(skill -> new SkillDTO(skill)).toList();
        }
    }

    @Data
    public static class NoticeListDTO{
        private Integer id;
        private String username;
        private String title;
        private String deadline;
        private String image;

        private List<SkillDTO> skills = new ArrayList<>();

        public NoticeListDTO(Notice notice) {
            this.id = notice.getId();
            this.username = notice.getUser().getUsername();
            this.title = notice.getTitle();
            this.deadline = notice.getDeadline();
            this.image = notice.getUser().getImage();

            this.skills = notice.getSkills().stream().map(skill -> new SkillDTO(skill)).toList();
        }
    }

    @Data
    public static class DetailDTO{
        private Integer id; // 공고 번호
        private Integer userId; // 기업 번호
        private String username; // 기업 이름
        private String address; // 기업 주소
        private String birth; // 기업 설립일
        private String title; // 공고 제목
        private String deadline; // 공고 마감일
        private String type; // 고용 형태
        private String field; // 개발 분야
        private String content; // 공고 내용
        private String workplace; // 기업 주소지
        private String email; // 기업 이메일
        private String tel; // 기업 연락처
        private Integer role; // 회원 구분
        private boolean isNoticeOwner;

        private List<SkillDTO> skills = new ArrayList<>();


        public DetailDTO(Notice notice, User sessionUser) {
            this.id = notice.getId();
            this.userId = notice.getUser().getId();
            this.username = notice.getUser().getUsername();
            this.address = notice.getUser().getAddress();
            this.birth = notice.getUser().getBirth();
            this.title = notice.getTitle();
            this.deadline = notice.getDeadline();
            this.type = notice.getType();
            this.field = notice.getField();
            this.content = notice.getContent();
            this.workplace = notice.getWorkPlace();
            this.email = notice.getUser().getEmail();
            this.tel = notice.getUser().getTel();
            this.role = notice.getUser().getRole();
            this.isNoticeOwner = false;
            if (sessionUser != null){
                if (sessionUser.getId() == userId) {
                    isNoticeOwner = true;
                }
            }
            this.skills = notice.getSkills().stream().map(skill -> new SkillDTO(skill)).toList();
        }

    }

    @Data
    public static class SkillDTO{
        private Integer id;
        private Integer noticeId;
        private String name;

        public SkillDTO(Skill skill) {
            this.id = skill.getId();
            this.noticeId = skill.getNotice().getId();
            this.name = skill.getName();
        }
    }


}