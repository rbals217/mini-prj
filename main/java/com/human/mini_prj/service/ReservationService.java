package com.human.mini_prj.service;

import com.human.mini_prj.dto.ReservationReqDto;
import com.human.mini_prj.dto.ReservationResDto;
import com.human.mini_prj.entity.Member;
import com.human.mini_prj.entity.Reservation;
import com.human.mini_prj.entity.Seat;
import com.human.mini_prj.repository.ConcertScheduleRepository;
import com.human.mini_prj.repository.MemberRepository;
import com.human.mini_prj.repository.ReservationRepository;
import com.human.mini_prj.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ReservationService {
    private final MemberRepository memberRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResDto createReservation(ReservationReqDto dto) {


        // 1. 회원이 있는지 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원이 없습니다."));


        List<Seat> seats = dto.getSeatNumbers().stream()
                .map(number -> seatRepository.findBySeatNumber(number))
                .toList();


        for (Seat seat : seats) {
            if (seat == null) {
                throw new RuntimeException("좌석이 없습니다.");
            }
        }
        // 예매 객체 생성
        Reservation reservation = new Reservation(); // 새로운 예매 정보 객체생성
        reservation.setMemberId(member.getId());        // 조회한 회원 id저장
        reservation.setScheduleId(dto.getScheduleId());     //요청한 공연id 불러오기
        reservation.setStatus("PENDING");               // 예매 상태를 PENDING 이라고 일단 설정

        Reservation saved = reservationRepository.save(reservation);        // 저장

        //좌석 1개 만 저장

        for (Seat seat : seats) {
            seat.setStatus("RESERVED");
        }

        return new ReservationResDto(saved);}

    @Transactional(readOnly = true)
    public ReservationResDto getReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("예매 정보가 없습니다."));

        return new ReservationResDto(reservation);

          }
        }
        ;
