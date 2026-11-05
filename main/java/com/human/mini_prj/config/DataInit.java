package com.human.mini_prj.config;

import com.human.mini_prj.entity.Board;
import com.human.mini_prj.entity.Comment;
import com.human.mini_prj.entity.Concert; // 콘서트 엔티티 임포트
import com.human.mini_prj.entity.Member;
import com.human.mini_prj.repository.BoardRepository;
import com.human.mini_prj.repository.CommentRepository;
import com.human.mini_prj.repository.ConcertRepository; // 콘서트 리포지토리 임포트
import com.human.mini_prj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataInit {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ConcertRepository concertRepository; // 콘서트 리포지토리 추가

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 1. 회원 더미 데이터 생성 (기존 로직 유지)
            if (memberRepository.count() == 0) {
                for (int i = 1; i <= 21; i++) {
                    Member member = new Member();
                    member.setEmail("user" + i + "@test.com");
                    member.setPwd("password123");
                    member.setName("테스트사용자" + i);
                    memberRepository.save(member);

                    Board board = new Board();
                    board.setTitle("테스트 제목 " + i);
                    board.setContent("테스트 내용 " + i);
                    board.setMember(member);
                    boardRepository.save(board);

                    Comment comment = new Comment();
                    comment.setContent("테스트 댓글입니다 " + i);
                    comment.setBoard(board);
                    comment.setMember(member);
                    commentRepository.save(comment);
                }
                System.out.println(">>> [DataInit] 회원, 게시글, 댓글 더미 생성 완료! <<<");
            }

            // 2. 콘서트(상품) 실제 같은 더미 데이터 40개 생성
            if (concertRepository.count() == 0) {
                String[] categories = {"뮤지컬", "콘서트", "클래식", "연극", "내한공연"};
                String[] locations = {"예술의전당 오페라극장", "세종문화회관 대극장", "블루스퀘어 신한카드홀", "LG아트센터 서울", "잠실실내체육관"};
                String[] posters = {
                        "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1460723237483-7a6dc9d0b212?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1526478806334-5fd488fcaabc?auto=format&fit=crop&w=800&q=80"
                };

                for (int i = 1; i <= 40; i++) {
                    Concert concert = new Concert();
                    // 카테고리 조합으로 다양한 제목 생성 (예: 뮤지컬 <테스트 공연 1>)
                    String category = categories[i % categories.length];
                    concert.setTitle(category + " <스타라이트 페스티벌 Vol." + i + ">");

                    // 가격을 조금씩 다르게 설정
                    concert.setPrice(70000 + (i * 2000));

                    // 포스터 이미지가 돌아가면서 들어가도록 설정
                    concert.setPosterUrl(posters[i % posters.length]);

                    // 8개 섹션별 상세 내용 동적 생성
                    concert.setNotice("공연 " + i + "회차 안내사항: 관람객 입장 시 티켓 및 신분증을 지참해 주세요.");
                    concert.setCasting("출연진 " + i + "팀: 대한민국 최고의 실력파 뮤지컬 배우 및 아티스트 군단");
                    concert.setDetail("전석 매진 신화에 빛나는 바로 그 공연! " + i + "번째 시즌으로 더욱 완벽해진 무대를 만나보세요.");
                    concert.setPriceInfo("VIP석 " + (70000 + (i * 2000)) + "원 / R석 110,000원 / S석 80,000원");
                    concert.setGuide("관람 연령: 중학생 이상 관람가 / 러닝타임: 160분 (인터미션 15분 포함)");
                    concert.setRefund("관람일 7일 전까지 취소 수수료 면제, 이후 규정에 따른 수수료 부과");
                    concert.setLocation(locations[i % locations.length]);

                    concertRepository.save(concert);
                }
                System.out.println(">>> [DataInit] 콘서트/상품 더미 데이터 40건 자동 생성 완료! <<<");
            }
        };
    }
}