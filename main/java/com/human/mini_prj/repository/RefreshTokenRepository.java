package com.human.mini_prj.repository;


import com.human.mini_prj.entity.Member;
import com.human.mini_prj.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Member member);
    void deleteByMember(Member member);     // 로그아웃 / 탈퇴 재사용 감지시 삭제
    void deleteByMember(String email);      // 괸라자가 강제 만료 ( 이메일 기준)
}
