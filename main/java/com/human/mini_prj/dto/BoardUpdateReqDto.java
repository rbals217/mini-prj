package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardUpdateReqDto {
    private Long id;
    private String title;
    private String content;
}
