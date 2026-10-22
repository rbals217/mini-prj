package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardLikeResDto {
    private boolean liked;
    private Long likeCount;

    public BoardLikeResDto(boolean liked, long likeCount) {
    }
}
