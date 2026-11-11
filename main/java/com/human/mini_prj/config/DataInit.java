
package com.human.mini_prj.config;

import com.human.mini_prj.entity.Concert;
import com.human.mini_prj.entity.ConcertSchedule;
import com.human.mini_prj.entity.Seat;

import com.human.mini_prj.repository.ConcertRepository;
import com.human.mini_prj.repository.ConcertScheduleRepository;
import com.human.mini_prj.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class DataInit {

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            // =====================================
            // 1. 콘서트(상품) 더미 데이터 40개 생성
            // =====================================

            if (concertRepository.count() == 0) {

                String[] categories = {
                        "뮤지컬", "콘서트", "클래식", "연극", "내한공연"
                };

                String[] locations = {
                        "예술의전당 오페라극장",
                        "세종문화회관 대극장",
                        "블루스퀘어 신한카드홀",
                        "LG아트센터 서울",
                        "잠실실내체육관"
                };

                String[] posters = {
                        "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1460723237483-7a6dc9d0b212?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&w=800&q=80",
                        "https://images.unsplash.com/photo-1526478806334-5fd488fcaabc?auto=format&fit=crop&w=800&q=80"
                };

                for (int i = 1; i <= 40; i++) {

                    Concert concert = new Concert();

                    String category = categories[i % categories.length];

                    concert.setTitle(
                            category + " <스타라이트 페스티벌 Vol." + i + ">"
                    );

                    concert.setPrice(70000 + (i * 2000));

                    concert.setPosterUrl(
                            posters[i % posters.length]
                    );

                    concert.setNotice(
                            "공연 " + i +
                                    "회차 안내사항: 관람객 입장 시 티켓 및 신분증을 지참해 주세요."
                    );

                    concert.setCasting(
                            "출연진 " + i +
                                    "팀: 대한민국 최고의 실력파 뮤지컬 배우 및 아티스트 군단"
                    );

                    concert.setDetail(
                            "전석 매진 신화에 빛나는 바로 그 공연! " +
                                    i + "번째 시즌으로 더욱 완벽해진 무대를 만나보세요."
                    );

                    concert.setPriceInfo(
                            "VIP석 " + (70000 + (i * 2000)) +
                                    "원 / R석 110,000원 / S석 80,000원"
                    );

                    concert.setGuide(
                            "관람 연령: 중학생 이상 관람가 / " +
                                    "러닝타임: 160분 (인터미션 15분 포함)"
                    );

                    concert.setRefund(
                            "관람일 7일 전까지 취소 수수료 면제, 이후 규정에 따른 수수료 부과"
                    );

                    concert.setLocation(
                            locations[i % locations.length]
                    );

                    concertRepository.save(concert);
                }

                System.out.println(
                        ">>> [DataInit] 콘서트 40건 생성 완료 <<<"
                );
            }

            // =====================================
            // 2. 모든 공연의 회차 및 좌석 자동 생성
            // =====================================

            createSchedulesAndSeats();

        };
    }

    // =====================================
    // 회차 및 좌석 자동 생성 메서드
    // =====================================

    private void createSchedulesAndSeats() {

        LocalDate startDate = LocalDate.of(2026, 11, 11);
        LocalDate endDate = LocalDate.of(2027, 12, 31);

        // 하루 2회 공연
        List<LocalTime> times = Arrays.asList(
                LocalTime.of(14, 0),
                LocalTime.of(19, 30)
        );

        // 좌석 번호 A1~A10, B1~B10, C1~C10
        List<String> seatNumbers = new ArrayList<>();

        for (char row = 'A'; row <= 'C'; row++) {
            for (int number = 1; number <= 10; number++) {
                seatNumbers.add(row + String.valueOf(number));
            }
        }

        // 모든 공연 조회
        List<Concert> concerts = concertRepository.findAll();

        int createdScheduleCount = 0;
        int createdSeatCount = 0;

        for (Concert concert : concerts) {

            Long concertId = concert.getId();

            // 해당 공연의 기존 회차 조회
            List<ConcertSchedule> schedules =
                    scheduleRepository
                            .findByConcertIdOrderByConcertDateAscConcertTimeAsc(
                                    concertId
                            );

            // 기존 회차 날짜와 시간 기록
            Set<String> existingSchedules = new HashSet<>();

            for (ConcertSchedule schedule : schedules) {

                existingSchedules.add(
                        schedule.getConcertDate() + "_" +
                                schedule.getConcertTime()
                );
            }

            // =====================================
            // 3. 회차 생성
            // =====================================

            List<ConcertSchedule> newSchedules = new ArrayList<>();

            for (LocalDate date = startDate;
                 !date.isAfter(endDate);
                 date = date.plusDays(1)) {

                for (LocalTime time : times) {

                    String key = date + "_" + time;

                    // 이미 존재하는 회차는 건너뛰기
                    if (existingSchedules.contains(key)) {
                        continue;
                    }

                    ConcertSchedule schedule = new ConcertSchedule();

                    schedule.setConcertId(concertId);
                    schedule.setConcertDate(date);
                    schedule.setConcertTime(time);

                    newSchedules.add(schedule);

                    // 같은 실행 중 중복 추가 방지
                    existingSchedules.add(key);
                }
            }

            // 회차 DB 저장
            List<ConcertSchedule> savedSchedules =
                    scheduleRepository.saveAll(newSchedules);

            createdScheduleCount += savedSchedules.size();

            // 기존 회차와 새 회차 합치기
            List<ConcertSchedule> allSchedules =
                    new ArrayList<>(schedules);

            allSchedules.addAll(savedSchedules);

            // =====================================
            // 4. 각 회차별 좌석 생성
            // =====================================

            for (ConcertSchedule schedule : allSchedules) {

                // 지정한 기간에 해당하는 회차만 처리
                if (schedule.getConcertDate().isBefore(startDate)
                        || schedule.getConcertDate().isAfter(endDate)) {
                    continue;
                }

                // 기존 좌석 조회
                List<Seat> existingSeats =
                        seatRepository.findBySchedule_Id(schedule.getId());

                Set<String> existingSeatNumbers = new HashSet<>();

                for (Seat seat : existingSeats) {
                    existingSeatNumbers.add(seat.getSeatNumber());
                }

                // 해당 회차에 없는 좌석만 생성
                List<Seat> newSeats = new ArrayList<>();

                for (String seatNumber : seatNumbers) {

                    if (existingSeatNumbers.contains(seatNumber)) {
                        continue;
                    }

                    Seat seat = new Seat();

                    seat.setSeatNumber(seatNumber);
                    seat.setStatus("AVAILABLE");

                    // 좌석별 가격
                    if (seatNumber.startsWith("A")) {
                        seat.setPrice(120000);
                    } else if (seatNumber.startsWith("B")) {
                        seat.setPrice(90000);
                    } else {
                        seat.setPrice(70000);
                    }

                    // 회차 연결
                    seat.setSchedule(schedule);

                    newSeats.add(seat);
                }

                // 회차별 좌석 저장
                if (!newSeats.isEmpty()) {

                    seatRepository.saveAll(newSeats);

                    createdSeatCount += newSeats.size();
                }
            }
        }

        // =====================================
        // 5. 실행 결과 출력
        // =====================================

        System.out.println("======================================");
        System.out.println(">>> 공연 회차 및 좌석 자동 생성 완료 <<<");
        System.out.println("전체 공연 수: " + concerts.size());
        System.out.println("새로 생성한 회차: " + createdScheduleCount);
        System.out.println("새로 생성한 좌석: " + createdSeatCount);
        System.out.println("회차별 좌석 수: 30개");
        System.out.println("공연 시간: 14:00 / 19:30");
        System.out.println("생성 기간: " + startDate + " ~ " + endDate);
        System.out.println("======================================");
    }
}
