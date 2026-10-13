package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardDeleteReqDto {
    private String Title;
    private String content;
    private Long id;
}
