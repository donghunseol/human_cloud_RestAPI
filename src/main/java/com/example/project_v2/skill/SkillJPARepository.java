package com.example.project_v2.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkillJPARepository extends JpaRepository<Skill, Integer> {

    @Query("select s from Skill s where s.notice.id = :notice_id")
    List<Skill> findByNoticeId(@Param("notice_id") int notice_id);

    @Query("select s from Skill s where s.resume.id = :resume_id")
    List<Skill> findByResumeId(@Param("resume_id") int resume_id);

    @Modifying
    @Query("delete from Skill s where s.resume.id = :id")
    void deleteAllByResumeId(@Param("id") Integer id);

    @Modifying
    @Query("delete from Skill s where s.notice.id = :id")
    void deleteAllByNoticeId(@Param("id") Integer id);
}