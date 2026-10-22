package com.human.mini_prj.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPostsCommentsResDto {
    private PageResDto<BoardResDto> posts;
    private PageResDto<CommentResDto> comments;

}
