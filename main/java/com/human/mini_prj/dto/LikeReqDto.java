package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeReqDto {
    private Long Member_id;
    private  Long Board_id;

    public LikeReqDto(Long Member_id, Long board_id) {
        this.Member_id = getMember_id();
        this.Board_id = getBoard_id();
    }

    public String getEmail() {

        return "";
    }
}
