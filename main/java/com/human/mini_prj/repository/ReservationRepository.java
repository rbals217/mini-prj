package com.human.mini_prj.repository;

import com.human.mini_prj.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByScheduleIdAndSeatNumberAndStatusNot(            //같은 공연 회차에 해당 좌석이 이미 예약되어 있는지 확인
            Long ScheduleId,
            String seatNumber,
            String Status
    );
}
