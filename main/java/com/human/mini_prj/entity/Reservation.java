package com.human.mini_prj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="reservation")
@Getter @Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;        // 예매 번호 (1번)
    private Long memberId;      // 회원 id
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private String status ="PENDING";

    private Long scheduleId;        // 공연 id
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ConcertSchedule concertSchedule;
    private LocalDateTime reservedAt = LocalDateTime.now();         // 날짜 불러오는거

    private String seatNumber;      // 좌석 넘버


}
