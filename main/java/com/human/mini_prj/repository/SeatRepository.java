package com.human.mini_prj.repository;


import com.human.mini_prj.entity.Seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findBySeatNumber(String seatNumber);

    List<Seat> findBySchedule_Id(Long scheduleId);
}

