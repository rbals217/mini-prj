package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByRegTimeDesc();
    // 제목 또는 내용에 키워드가 포함된 게시글 (페이지네이션)
    // SQL : where title like %?% or content like %?%
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}