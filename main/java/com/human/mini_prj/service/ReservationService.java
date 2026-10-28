package com.human.mini_prj.service;

import com.human.mini_prj.dto.ReservationReqDto;
import com.human.mini_prj.dto.ReservationResDto;
import com.human.mini_prj.entity.*;
import com.human.mini_prj.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.human.mini_prj.repository.ReservationSeatRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReservationService {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;

    @Transactional
    public ReservationResDto createReservation(ReservationReqDto dto) {


        // 1. 회원이 있는지 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원이 없습니다."));

        // 2. 공연 회차가 확인
        ConcertSchedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("공연 회차가 존재하지 않습니다"));

        //좌석 조회 및 존재 여부 확인
        List<Seat> seats = new ArrayList<>();
        for (String seatNumber : dto.getSeatNumbers()) {
            Seat seat = seatRepository
                    .findBySchedule_IdAndSeatNumber(
                            dto.getScheduleId(),
                            seatNumber
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    seatNumber + "좌석이 해당 공연 회차에 없습니다."
                            )
                    );

            seats.add(seat);
        }


        // 예매 객체 생성
        Reservation reservation = new Reservation(); // 새로운 예매 정보 객체생성
        reservation.setMember(member);        // 조회한 회원 id저장
        reservation.setConcertSchedule(schedule);
        reservation.setStatus("PENDING");             // 예매 상태를 PENDING 이라고 일단 설정

        Reservation saved = reservationRepository.save(reservation);        // 저장

        // 선택한 좌석들을 예매 정보에 연결해야함
        for (Seat seat : seats) {
            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setReservation(saved);
            reservationSeat.setSeat(seat);

            reservationSeatRepository.save(reservationSeat);
        }
        return new ReservationResDto(saved);
    }

    @Transactional(readOnly = true)
    public ReservationResDto getReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("예매 정보가 없습니다."));

        return new ReservationResDto(reservation);

          }
        }

