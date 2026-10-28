package com.human.mini_prj.repository;

import com.human.mini_prj.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
    // 선택한 회차 조회

public interface ScheduleRepository extends JpaRepository<ConcertSchedule, Long> {

    List<ConcertSchedule>
    findByConcertIdOrderByConcertDateAscConcertTimeAsc(Long ConcertId);

}
