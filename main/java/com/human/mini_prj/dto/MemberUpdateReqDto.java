package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원 정보 수정 요청 DTO
@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateReqDto {

    private String name;
    private String image;
}