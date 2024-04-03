package com.example.project_v2.resume;

import com.example.project_v2.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeJPARepository extends JpaRepository<Resume, Integer> {
    @Query("select r from Resume r join fetch r.user u where r.id = :id")
    Optional<Resume> findByIdJoinUser(@Param("id") int id);

    @Query("select r from Resume r join fetch r.skills s where s.name like %:skillName%")
    List<Resume> findBySkill(@Param("skillName") String skillName, Pageable pageable);

    @Query("select r from Resume r join fetch r.user u where u = :user")
    List<Resume> findByUser(@Param("user") User user, Pageable pageable);
}
