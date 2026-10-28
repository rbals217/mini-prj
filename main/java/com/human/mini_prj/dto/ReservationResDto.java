package com.human.mini_prj.dto;

import com.human.mini_prj.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ReservationResDto {


    private final Long reservationId;
    private final Long memberId;
    private final Long scheduleId;
    private final String status;
    private final String seatNumber;
    private LocalDateTime reservedAt;       // 예매 정보를 시간과 날짜로 저장하는 역할

    public ReservationResDto(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.memberId = reservation.getMemberId();
        this.scheduleId = reservation.getScheduleId();
        this.status = reservation.getStatus();
        this.seatNumber = reservation.getSeatNumber();
        this.reservedAt = reservation.getReservedAt();
    }
}
