package com.human.mini_prj.repository;

import com.human.mini_prj.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository
        extends JpaRepository<BoardLike, Long> {

    Optional<BoardLike> findByBoardIdAndMemberEmail(
            Long boardId, String email);


    long countByBoardId(Long boardID);
}