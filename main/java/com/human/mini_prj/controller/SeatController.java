package com.human.mini_prj.controller;

import com.human.mini_prj.dto.SeatResDto;
import com.human.mini_prj.entity.Seat;
import com.human.mini_prj.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SeatController {

    private final SeatService seatService;
        //좌석 등록
    @PostMapping("/demo/{scheduleId}")
    public String createDemoSeats(
            @PathVariable Long scheduleId) {
        return seatService.createDemoSeats(scheduleId);
    }
    //좌석 조회
    @GetMapping("/{scheduleId}")
    public List<SeatResDto>getSeats(@PathVariable Long scheduleId) {
        return seatService.getSeats(scheduleId);
    }
}