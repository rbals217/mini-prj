package com.human.mini_prj.dto;

import com.human.mini_prj.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.net.openssl.OpenSSLStatus;

@Getter
@Setter
@NoArgsConstructor
public class BoardResDto {
    private Long id;
    private String title;
    private String content;
    private String email;
    private String name;
    private String regDate;


}