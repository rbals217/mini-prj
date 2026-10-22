package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoard_IdOrderByRegDateDesc(Long boardId);

    long countByBoardId(Long boardId);
}