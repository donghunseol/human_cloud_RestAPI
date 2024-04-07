package com.example.project_v2.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplyJPARepository extends JpaRepository<Apply, Integer> {

    // 지원에서 이력서를 조인 시켜 조회함
    @Query("SELECT a FROM Apply a JOIN FETCH a.resume WHERE a.id = :id")
    Optional<Apply> findById(@Param("id") Integer id);
    @Query("SELECT a FROM Apply a JOIN FETCH a.user u JOIN FETCH a.notice n JOIN FETCH a.resume r WHERE a.user.id = :userId")
    List<Apply> findAppliesByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("delete from Apply a where a.resume.id = :id")
    void deleteByResumeId(@Param("id") Integer id);

    @Modifying
    @Query("delete from Apply a where a.notice.id = :id")
    void deleteByNoticeId(@Param("id") Integer id);

    // 특정 공고에 지원한 인원 출력
    @Query("SELECT a FROM Apply a JOIN FETCH a.user u JOIN FETCH a.notice n WHERE a.notice.user.id = :userId")
    List<Apply> findAppliesByNoticeUserId(@Param("userId") Integer userId);

    // 특정 이력서로 지원한 인원 출력
    @Query("SELECT a FROM Apply a JOIN FETCH a.user u JOIN FETCH a.notice n WHERE a.resume.user.id = :userId")
    List<Apply> findAppliesByResumeUserId(@Param("userId") Integer userId);
}
