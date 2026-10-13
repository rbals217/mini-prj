package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원가입 요청 DTO
@Getter
@Setter
@NoArgsConstructor

public class SIgnUpReqDto {
    private  String email;
    private  String pwd;
    private  String name;
}
