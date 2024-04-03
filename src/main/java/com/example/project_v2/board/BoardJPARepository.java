package com.example.project_v2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardJPARepository extends JpaRepository<Board, Integer> {
    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") int id);

    @Query("SELECT b FROM Board b JOIN FETCH b.user u ORDER BY b.id DESC")
    List<Board> findAllWithUser();
}
