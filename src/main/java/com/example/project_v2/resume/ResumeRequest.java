package com.example.project_v2.resume;

import com.example.project_v2.skill.Skill;
import com.example.project_v2.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

public class ResumeRequest {
    @Data
    public static class SaveDTO {
        private User user;

        @Size(min = 1, max = 20, message = "제목은 20자를 초과할 수 없습니다")
        @NotEmpty(message = "제목을 입력해주세요")
        private String title; // 이력서 제목

        @NotEmpty(message = "경력을 입력해주세요")
        private String career; // 경력

        @NotEmpty(message = "자격증을 입력해주세요")
        private String license; // 자격증

        @NotEmpty(message = "학력을 입력해주세요")
        private String education; // 학력

        @NotEmpty(message = "전공을 입력해주세요")
        private String major; // 전공
        private List<SkillDTO> skills = new ArrayList<>();

        @Data
        public static class SkillDTO{
            private String name;
            private Integer role;
            private Integer resumeId;
        }

        public Resume toEntity(User user) {
            Resume resume = new Resume();
            resume.setUser(user);
            resume.setTitle(this.title);
            resume.setCareer(this.career);
            resume.setLicense(this.license);
            resume.setEducation(this.education);
            resume.setMajor(this.major);

            return resume;
        }
    }

    @Data
    public static class UpdateDTO{
        private User user;

        @Size(min = 1, max = 20, message = "제목은 20자를 초과할 수 없습니다")
        @NotEmpty(message = "제목을 입력해주세요")
        private String title; // 이력서 제목

        @NotEmpty(message = "경력을 입력해주세요")
        private String career; // 경력

        @NotEmpty(message = "자격증을 입력해주세요")
        private String license; // 자격증

        @NotEmpty(message = "학력을 입력해주세요")
        private String education; // 학력

        @NotEmpty(message = "전공을 입력해주세요")
        private String major; // 전공
        private List<SkillDTO> skills = new ArrayList<>();

        @Data
        public static class SkillDTO{
            private Resume resume;
            private String name;
            private Integer role;

            public Skill toEntity(){
                return Skill.builder()
                        .resume(resume)
                        .name(name)
                        .role(role)
                        .build();
            }
        }

        public Resume toEntity(User sessionUser){
            List<Skill> skillList = this.skills.stream()
                    .map(SkillDTO::toEntity) // SkillDTO를 Skill 엔티티로 변환
                    .toList();

            return Resume.builder()
                    .user(sessionUser)
                    .title(title)
                    .career(career)
                    .license(license)
                    .education(education)
                    .major(major)
                    .skills(skillList)
                    .build();
        }
    }
}
