package com.example.project_v2.scrap;

import com.example.project_v2.notice.Notice;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name="scrap_tb")
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 스크랩 ID

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 스크랩한 유저 번호, 스크랩한 유저의 role 값

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume; // 이력서 번호

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Notice notice; // 공고 번호

    @CreationTimestamp
    private Timestamp createdAt; // 생성 일자

    @Builder
    public Scrap(Integer id, User user, Resume resume, Notice notice, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.resume = resume;
        this.notice = notice;
        this.createdAt = createdAt;
    }
}
