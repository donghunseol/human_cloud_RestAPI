package com.example.project_v2.notice;

import com.example.project_v2.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoticeJPARepository extends JpaRepository<Notice, Integer> {

    @Query("select n from Notice n join fetch n.user u where n.id = :id")
    Optional<Notice> findByIdJoinUser(@Param("id") Integer id);

    @Query("select n from Notice n join fetch n.skills s where s.name like %:skillName%")
    List<Notice> findBySkill(@Param("skillName") String skillName, Pageable pageable);

    @Query("select n from Notice n join fetch n.user u where u = :user")
    List<Notice> findByUser(@Param("user") User user, Pageable pageable);
}
