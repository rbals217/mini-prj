package com.human.mini_prj.rag;


import com.human.mini_prj.entity.Concert;
import com.human.mini_prj.rag.dto.IndexReqDto;
import com.human.mini_prj.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RagIndexService {

    private final ConcertRepository concertRepository;
    private final RagClient ragClient;

    // DB에 저장된 전체 공연 색인
    public int indexAllConcerts() {

        // 1. DB에서 공연 전체 조회
        List<Concert> concerts = concertRepository.findAll();

        int totalChunks = 0;

        // 2. 공연 하나씩 FastAPI로 전송
        for (Concert concert : concerts) {

            IndexReqDto req = new IndexReqDto(
                    concert.getId(),
                    concert.getTitle(),
                    concert.getPrice(),
                    concert.getLocation(),
                    concert.getNotice(),
                    concert.getCasting(),
                    concert.getDetail(),
                    concert.getPriceInfo(),
                    concert.getGuide(),
                    concert.getRefund(),
                    concert.getStory(),
                    concert.getMood()
            );

            // 3. 색인 결과 청크 수 누적
            totalChunks += ragClient.index(req);
        }
        return totalChunks;
    }
}


