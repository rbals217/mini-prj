//package com.human.mini_prj.repository;
//
//import com.human.mini_prj.entity.BoardLike;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
//    // 특정 게시글에 특정 회원이 좋아요를 눌렀는지 찾기 (토글 판별용)
//    Optional<BoardLike> findByBoardIdAndMemberEmail(Long boardId, String email);
//
//    // 특정 게시글의 총 좋아요 개수 세기
//    long countByBoardId(Long boardId);
//}