package com.example.project_v2.notice;

import com.example.project_v2.skill.Skill;
import com.example.project_v2.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class NoticeRequest {

    // 공고 수정
    @Data
    public static class UpdateDTO{
        private User user;

        @Size(min = 1, max = 20, message = "제목은 20차를 초과할 수 없습니다")
        @NotEmpty(message = "제목을 입력해주세요")
        private String title; // 공고 제목

        @NotEmpty(message = "고용 형태를 입력해주세요")
        private String type; // 고용 형태

        @NotEmpty(message = "분야를 입력해주세요")
        private String field; // 분야

        @NotEmpty(message = "근무지를 입력해주세요")
        private String workPlace; // 근무지

        @NotEmpty(message = "마감일을 입력해주세요")
        private String deadline; // 마감일

        @Size(max = 300, message = "상세 내용은 300자를 초과할 수 없습니다")
        @NotEmpty(message = "상세 내용을 입력해주세요")
        private String content; // 상세 내용
        private List<SkillDTO> skills = new ArrayList<>();

        @Data
        public static class SkillDTO{
            private Notice notice;

            @NotEmpty(message = "스킬을 입력해주세요")
            private String name;
            private Integer role;

            public Skill toEntity(){
                return Skill.builder()
                        .notice(notice)
                        .name(name)
                        .role(role)
                        .build();
            }
        }

        public Notice toEntity(User sessionUser){
            List<Skill> skillEntities = this.skills.stream()
                    .map(SkillDTO::toEntity) // SkillDTO를 Skill 엔티티로 변환
                    .toList();

            return Notice.builder()
                    .user(sessionUser)
                    .title(title)
                    .type(type)
                    .field(field)
                    .workPlace(workPlace)
                    .deadline(deadline)
                    .content(content)
                    .skills(skillEntities)
                    .build();
        }
    }

    // 공고 등록(저장)
    @Data
    public static class SaveDTO{
        private User user;

        @Size(min = 1, max = 20, message = "제목은 20차를 초과할 수 없습니다")
        @NotEmpty(message = "제목을 입력해주세요")
        private String title; // 공고 제목

        @NotEmpty(message = "고용 형태를 입력해주세요")
        private String type; // 고용 형태

        @NotEmpty(message = "분야를 입력해주세요")
        private String field; // 분야

        @NotEmpty(message = "근무지를 입력해주세요")
        private String workPlace; // 근무지

        @NotEmpty(message = "마감일을 입력해주세요")
        private String deadline; // 마감일

        @Size(max = 300, message = "상세 내용은 300자를 초과할 수 없습니다")
        @NotEmpty(message = "상세 내용을 입력해주세요")
        private String content; // 상세 내용

        private List<SkillDTO> skills = new ArrayList<>();

//        @Data
//        public static class UserDTO{
//            private Integer id;
//            private String name;
//            private String email;
//            private String tel;
//            private String address;
//            private String birth;
//
//            public User toEntity(){
//                return User.builder()
//                        .id(id)
//                        .name(name)
//                        .email(email)
//                        .tel(tel)
//                        .address(address)
//                        .birth(birth)
//                        .build();
//            }
//        }

        @Data
        public static class SkillDTO{
            private Notice notice;

            @NotEmpty(message = "스킬을 입력해주세요")
            private String name;
            private Integer role;

            public Skill toEntity(){
                return Skill.builder()
                        .notice(notice)
                        .name(name)
                        .role(role)
                        .build();
            }
        }

        public Notice toEntity(User sessionUser){
            List<Skill> skillEntities = this.skills.stream()
                    .map(SkillDTO::toEntity) // SkillDTO를 Skill 엔티티로 변환
                    .toList();

            return Notice.builder()
                    .user(sessionUser)
                    .title(title)
                    .type(type)
                    .field(field)
                    .workPlace(workPlace)
                    .deadline(deadline)
                    .content(content)
                    .skills(skillEntities)
                    .build();
        }
    }
}
