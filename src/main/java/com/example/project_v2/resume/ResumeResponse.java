package com.example.project_v2.resume;

import com.example.project_v2.skill.Skill;
import com.example.project_v2.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResumeResponse {

    @Data
    public static class DTO{
        private Integer id;
        private Integer userId;
        private String title;
        private String career;
        private String license;
        private String education;
        private String major;

        private List<SkillDTO> skills = new ArrayList<>();

        public DTO(Resume resume, User sessionUser) {
            this.id = resume.getId();
            this.userId = sessionUser.getId();
            this.title = resume.getTitle();
            this.career = resume.getCareer();
            this.license = resume.getLicense();
            this.education = resume.getEducation();
            this.major = resume.getMajor();

            this.skills = resume.getSkills().stream().map(skill -> new SkillDTO(skill)).toList();
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String name;
        private String title;
        private String email;
        private String tel;
        private String address;
        private String image;
        private String career;
        private String license;
        private String education;
        private String major;
        private Integer userId;
        private boolean isResumeOwner;

        private List<SkillDTO> skills = new ArrayList<>();


        public DetailDTO(Resume resume, User sessionUser) {
            this.id = resume.getId();
            this.name = resume.getUser().getName();
            this.title = resume.getTitle();
            this.email = resume.getUser().getEmail();
            this.tel = resume.getUser().getTel();
            this.address = resume.getUser().getAddress();
            this.image = resume.getUser().getImage();
            this.career = resume.getCareer();
            this.license = resume.getLicense();
            this.education = resume.getEducation();
            this.major = resume.getMajor();
            this.userId = resume.getUser().getId();
            this.isResumeOwner = false;
            if (sessionUser != null) {
                if (sessionUser.getId() == userId) {
                    isResumeOwner = true;
                }
            }
            this.skills = resume.getSkills().stream().map(skill -> new SkillDTO(skill)).toList();
        }
    }

    @Data
    public static class ResumeListDTO {
        private Integer id;
        private String title;
        private String username;
        private String image;
        private Integer userId;

        private List<SkillDTO> skills = new ArrayList<>();

        public ResumeListDTO(Resume resume) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.username = resume.getUser().getUsername();
            this.image = resume.getUser().getImage();
            this.userId = resume.getUser().getId();

            this.skills = resume.getSkills().stream().map(skill -> new SkillDTO(skill)).toList();
        }
    }

    @Data
    public static class SkillDTO {
        private Integer id;
        private int resumeId;
        private String name;

        public SkillDTO(Skill skill) {
            this.id = skill.getId();
            this.resumeId = skill.getResume().getId();
            this.name = skill.getName();
        }
    }
}
