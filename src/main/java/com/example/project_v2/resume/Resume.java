package com.example.project_v2.resume;

import com.example.project_v2.skill.Skill;
import com.example.project_v2.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Table(name = "resume_tb")
@Data
@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;

    @Column(nullable = false)
    private String career;

    @Column(nullable = false)
    private String license;

    @Column(nullable = false)
    private String education;

    @Column(nullable = false)
    private String major;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Skill> skills = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Resume(int id, User user, String title, String career, String license, String education, String major, List<Skill> skills) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.career = career;
        this.license = license;
        this.education = education;
        this.major = major;
        this.skills = skills;
    }
}
