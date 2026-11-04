package com.human.mini_prj.controller;

import lombok.RequiredArgsConstructor;

import com.human.mini_prj.entity.Concert;
import com.human.mini_prj.service.ConcertService;
import com.human.mini_prj.service.ConcertScheduleService;
import com.human.mini_prj.dto.ScheduleResDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concerts")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;
    private final ConcertScheduleService concertScheduleService;

    // 특정 공연 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Concert> getReservationDetail(
            @PathVariable Long id) {

        Concert concert = concertService.getReservationDetail(id);

        return ResponseEntity.ok(concert);
    }

    // 전체 공연 목록 조회
    @GetMapping("")
    public ResponseEntity<List<Concert>> getAllConcerts() {

        List<Concert> list = concertService.getAllConcerts();

        return ResponseEntity.ok(list);
    }

    // 특정 공연의 회차 목록 조회
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<List<ScheduleResDto>> getSchedules(
            @PathVariable Long concertId) {

        List<ScheduleResDto> schedules =
                concertScheduleService.getSchedule(concertId);

        return ResponseEntity.ok(schedules);
    }
}