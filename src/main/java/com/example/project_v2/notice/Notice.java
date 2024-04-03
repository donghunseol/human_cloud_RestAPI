package com.example.project_v2.notice;

import com.example.project_v2.skill.Skill;
import com.example.project_v2.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Table(name = "notice_tb")
@Data
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String field;

    private String workPlace;
    private String content;

    @Column(nullable = false)
    private String deadline;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Skill> skills = new ArrayList<>();


    @Builder
    public Notice(Integer id, User user, String title, String type, String field, String workPlace, String content, String deadline, List<Skill> skills) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.type = type;
        this.field = field;
        this.workPlace = workPlace;
        this.content = content;
        this.deadline = deadline;
        if (skills != null) { // Builder를 사용할 때 skills 리스트가 제공되면 해당 리스트의 각 Skill에 현재 Notice를 설정
            skills.forEach(this::addSkill);
        }
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
        skill.setNotice(this);
    }
}