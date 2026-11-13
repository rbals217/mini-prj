
package com.human.mini_prj.service;

import com.human.mini_prj.dto.MemberResDto;
import com.human.mini_prj.dto.SignUpReqDto;
import com.human.mini_prj.dto.TokenDto;
import com.human.mini_prj.entity.Member;
import com.human.mini_prj.entity.RefreshToken;
import com.human.mini_prj.exception.CustomException;
import com.human.mini_prj.repository.MemberRepository;
import com.human.mini_prj.repository.RefreshTokenRepository;
import com.human.mini_prj.security.SecurityUtil;
import com.human.mini_prj.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원 가입 여부 확인
    public boolean isDuplicatedEmail(String userId) {
        return memberRepository.existsByEmail(userId);
    }

    // 회원 가입
    public boolean signUp(SignUpReqDto dto) {
        try {
            Member member = toEntity(dto);
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            log.error("회원 가입 시 오류 발생: {}", e.getMessage(), e);
            return false;
        }
    }

    // 로그인
    public TokenDto login(String userId, String pwd) {

        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(this::loginFailed);

        if (!passwordEncoder.matches(pwd, member.getPwd())) {
            throw loginFailed();
        }

        TokenDto tokenDto =
                tokenProvider.createToken(userId, member.getAuthority());

        saveRefreshToken(member, tokenDto);

        log.info("로그인 성공: {}", userId);

        return tokenDto;
    }

    // Refresh Token 재발급
    @Transactional(noRollbackFor = CustomException.class)
    public TokenDto reissue(String refreshToken) {

        log.info("========== 토큰 재발급 시작 ==========");

        // ① Refresh Token 검증
        log.info("① Refresh Token 검증 시작");

        if (!tokenProvider.validate(refreshToken, TokenProvider.REFRESH)) {
            log.error("① Refresh Token 검증 실패");
            throw invalidRefresh();
        }

        log.info("① Refresh Token 검증 성공");

        // ② 토큰에서 회원 ID 추출
        String userId = tokenProvider.getUserId(refreshToken);

        log.info("② 토큰 회원 ID 추출 성공: {}", userId);

        // ③ 회원 조회
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("③ 회원 조회 실패: {}", userId);
                    return invalidRefresh();
                });

        log.info("③ 회원 조회 성공: {}", userId);

        // ④ DB에 저장된 Refresh Token 조회
        RefreshToken saved = refreshTokenRepository.findByMember(member)
                .orElseThrow(() -> {
                    log.error("④ DB에 Refresh Token이 존재하지 않음: {}", userId);
                    return invalidRefresh();
                });

        log.info("④ DB Refresh Token 조회 성공");

        // ⑤ 요청 토큰과 DB 토큰 비교
        if (!saved.getToken().equals(refreshToken)) {

            log.warn("⑤ Refresh Token 불일치: {}", userId);

            refreshTokenRepository.delete(saved);

            log.warn("DB Refresh Token 삭제 완료: {}", userId);

            throw new CustomException(
                    HttpStatus.UNAUTHORIZED,
                    "REFRESH_REUSED",
                    "토큰 재사용이 감지되어 로그아웃되었습니다. 다시 로그인하세요."
            );
        }

        log.info("⑤ Refresh Token 일치 확인");

        // ⑥ 새 토큰 발급
        TokenDto newToken = tokenProvider.createToken(
                member.getUserId(),
                member.getAuthority()
        );

        // ⑦ DB에 새 Refresh Token 저장
        saved.setToken(newToken.refreshToken());
        saved.setExpiresAt(
                toLocalDateTime(newToken.refreshTokenExpiresIn())
        );

        log.info("⑥ 새 토큰 발급 완료");
        log.info("⑦ DB Refresh Token 갱신 완료");
        log.info("========== 토큰 재발급 종료 ==========");

        return newToken;
    }

    // 로그아웃
    public void logout(String userId) {

        memberRepository.findByUserId(userId)
                .ifPresent(refreshTokenRepository::deleteByMember);

        log.info("로그아웃 처리 완료: {}", userId);
    }

    // 관리자 강제 만료
    public void revoke(String userId) {

        refreshTokenRepository.deleteByMemberUserId(userId);

        log.info("Refresh Token 강제 만료 처리: {}", userId);
    }

    // 마이페이지 회원 정보 조회
    @Transactional(readOnly = true)
    public MemberResDto getMemberInfo() {

        String userId = SecurityUtil.currentUserId();

        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        MemberResDto dto = new MemberResDto();

        dto.setId(member.getId());
        dto.setUserId(member.getUserId());
        dto.setEmail(member.getEmail());
        dto.setName(member.getName());
        dto.setImage(member.getImage());
        dto.setPhone(member.getPhone());
        dto.setRegDate(member.getRegDate());

        return dto;
    }

    // 회원당 Refresh Token 1개 저장
    private void saveRefreshToken(Member member, TokenDto tokenDto) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByMember(member)
                        .orElseGet(() -> new RefreshToken(member));

        refreshToken.setToken(tokenDto.refreshToken());
        refreshToken.setExpiresAt(
                toLocalDateTime(tokenDto.refreshTokenExpiresIn())
        );

        refreshTokenRepository.save(refreshToken);
    }

    private LocalDateTime toLocalDateTime(long epochMillis) {

        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(epochMillis),
                ZoneId.systemDefault()
        );
    }

    private CustomException loginFailed() {

        return new CustomException(
                HttpStatus.UNAUTHORIZED,
                "LOGIN_FAILED",
                "이메일 또는 비밀번호가 올바르지 않습니다."
        );
    }

    private CustomException invalidRefresh() {

        return new CustomException(
                HttpStatus.UNAUTHORIZED,
                "INVALID_REFRESH",
                "Refresh Token이 유효하지 않습니다. 다시 로그인하세요."
        );
    }

    // 아이디 중복 확인
    public boolean existsUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    // 회원가입 DTO → Entity
    private Member toEntity(SignUpReqDto dto) {

        Member member = new Member();

        member.setName(dto.getName());
        member.setPwd(passwordEncoder.encode(dto.getPwd()));
        member.setUserId(dto.getUserId());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());

        return member;
    }
}
