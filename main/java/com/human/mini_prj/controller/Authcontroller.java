package com.human.mini_prj.controller;

import com.human.mini_prj.dto.LoginReqDto;
import com.human.mini_prj.dto.SIgnUpReqDto;
import com.human.mini_prj.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j      // log 메시지 출력
@RestController         // Restfull API
@RequestMapping("/auth")    // 직렬화, 역직렬화 //  http://localhost:8111/auth/
@RequiredArgsConstructor


public class Authcontroller {
    private final AuthService authService; // 생성자를 통한 의존성 주입

    // 회원 가입 여부 확인
   @GetMapping("/exists/{email}")
    public boolean existsEmail(@PathVariable String email) {
       return authService.isDuplicatedEmail(email);

   }
    // 회원 가입
    @PostMapping("/signup")
    public  ResponseEntity<Boolean> singUp(@RequestBody SIgnUpReqDto dto) {
       return ResponseEntity.ok(authService.signUp(dto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login (@RequestBody LoginReqDto dto) {
       return ResponseEntity.ok(authService.login(dto.getEmail(), dto.getPwd()));
    }
}
