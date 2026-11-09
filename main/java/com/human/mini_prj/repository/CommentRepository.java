package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 게시글에 대한 댓글 목록 조회(최신순)
    List<Comment> findByBoardIdOrderByRegDateDesc(Long boardId);

    // 특정 회원이 쓴 댓글
    Page<Comment> findByMemberEmail(String email, Pageable pageable);

    // 게시글별 댓글 수
    Long countByBoardId(Long boardId);

    // 게시글 삭제 시 댓글 먼저 삭제
    void deleteByBoardId(Long boardId);
}