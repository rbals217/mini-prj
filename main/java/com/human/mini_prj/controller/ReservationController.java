package com.human.mini_prj.controller;

import com.human.mini_prj.dto.ReservationReqDto;
import com.human.mini_prj.dto.ReservationResDto;
import com.human.mini_prj.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping        // 예매 정보 조회 주소
    public ReservationResDto createReservation(
            @RequestBody ReservationReqDto dto) {
        return reservationService.createReservation(dto);
    }
    @GetMapping("/{id}")        // service 호출
    public ReservationResDto getReservation(@PathVariable Long id) {
        return reservationService.getReservation(id);
    }

}
