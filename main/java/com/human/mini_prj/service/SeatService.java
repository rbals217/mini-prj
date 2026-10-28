package com.human.mini_prj.service;

import com.human.mini_prj.dto.SeatResDto;
import com.human.mini_prj.entity.Seat;
import com.human.mini_prj.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    // 좌석 등록 API 테스트
    @Transactional
    public String createDemoSeats(Long scheduleId) {
        return "좌석 등록 API 호출 성공! scheduleId = " + scheduleId;
    }

    // 좌석 조회
    public List<SeatResDto> getSeats(Long scheduleId) {

        List<Seat> seats =
                seatRepository.findBySchedule_Id(scheduleId);

        List<SeatResDto> result = new ArrayList<>();

        for (Seat seat : seats) {
            result.add(new SeatResDto(seat));
        }

        return result;
    }
}