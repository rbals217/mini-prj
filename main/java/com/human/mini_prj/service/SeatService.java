package com.human.mini_prj.service;

import com.human.mini_prj.dto.SeatResDto;
import com.human.mini_prj.entity.Seat;
import com.human.mini_prj.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    public List<SeatResDto> getSeats(Long scheduleId) {     // 좌석조회

        List<Seat> seats =seatRepository.findBySchedule_Id(scheduleId); // db내 좌석목록 조회

    List<SeatResDto> result = new ArrayList<>();        //빈객체생성

    for (Seat seat : seats) {
        result.add(new SeatResDto(seat));       // for 문으로 하나씩 생성
    }
    return result;
    }

    }

