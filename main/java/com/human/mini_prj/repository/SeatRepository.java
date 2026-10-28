package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findBySeatNumber(String seatNumber);
}
