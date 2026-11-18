package com.human.mini_prj.dto;

import com.human.mini_prj.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class ReservationResDto {

    private String concertTitle;
    private String posterUrl;
    private final Long reservationId;
    private Long concertId;
    private final Long scheduleId;  // 공연번호
    private final String status;
    private LocalDate concertDate;  //공연 날짜
    private LocalTime concertTime;  // 공연 시간
    private LocalDateTime reservedAt;       // 예매 정보를 시간과 날짜로 저장하는 역할

    // 예매한 좌석 번호
    private  List<String> seatNumbers;

    // 예매한 좌석들의 총 가격
    private Integer totalPrice;

    public ReservationResDto(Reservation reservation) {



        // 예약 Id
        this.reservationId = reservation.getId();

        //예약된 공연 회차 정보
        if(reservation.getConcertSchedule()!=null) {
            // 공연 회차 ID
            this.scheduleId = reservation.getConcertSchedule().getId();

            //실제 공연 ID
            this.concertId= reservation.getConcertSchedule().getConcertId();

            // 공연 시각
            this.concertTime= reservation.getConcertSchedule().getConcertTime();
            // 공연 날짜 추가
            this.concertDate = reservation .getConcertSchedule().getConcertDate();
        } else {
            this.scheduleId =null;
        }
        this.status = reservation.getStatus();

        // 예매한 날짜와 시간
        this.reservedAt = reservation.getReservedAt();
        // 예약한 좌석번호 가져오기
        this.seatNumbers = reservation.getReservationSeats().stream()
                .map(rs -> rs.getSeat().getSeatNumber())
                .collect(Collectors.toList());



    }

}