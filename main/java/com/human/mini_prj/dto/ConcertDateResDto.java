package com.human.mini_prj.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ConcertDateResDto {

    private Long concertId;
    private String title;
    private String posterUrl;
    private String location;
    private LocalDate concertDate;
    private List<LocalTime> concertTimes;

    public ConcertDateResDto(
            Long concertId,
            String title,
            String posterUrl,
            String location,
            LocalDate concertDate,
            List<LocalTime> concertTimes
    ) {
        this.concertId = concertId;
        this.title =title;
        this.posterUrl = posterUrl;
        this.location = location;
        this.concertDate = concertDate;
        this.concertTimes = concertTimes;
    }
}
