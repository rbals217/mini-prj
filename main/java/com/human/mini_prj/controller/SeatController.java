package com.human.mini_prj.controller;

import com.human.mini_prj.dto.SeatResDto;
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
    @GetMapping("/{scheduleId}")
    public List<SeatResDto> getSeats(
            @PathVariable Long scheduleId) {
        return seatService.getSeats(scheduleId);
    }
}
