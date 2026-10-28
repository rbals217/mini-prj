package com.human.mini_prj.service;

import com.human.mini_prj.dto.ScheduleResDto;
import com.human.mini_prj.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertScheduleService {
    private  final ScheduleRepository scheduleRepository;

    public List<ScheduleResDto> getSchedule(Long concertId) {
        return scheduleRepository
                .findByConcertIdOrderByConcertDateAscConcertTimeAsc(concertId)
                .stream()
                .map(ScheduleResDto::new)
                .toList();
    }
}
