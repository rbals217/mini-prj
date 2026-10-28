package com.human.mini_prj.repository;


import com.human.mini_prj.entity.Seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findBySchedule_Id(Long scheduleId);
    Optional<Seat>findBySchedule_IdAndSeatNumber(// 공연 회차 ID와 좌석 번호가 모두 일치하는 좌석을 찾아줌
            Long scheduleId, String seatNumber
    );
}

