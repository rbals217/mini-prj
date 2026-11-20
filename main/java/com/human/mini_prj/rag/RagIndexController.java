package com.human.mini_prj.rag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
@RequiredArgsConstructor
public class RagIndexController {

    private final RagIndexService ragIndexService;

    // DB에 저장된 전체 공연 색인
    @PostMapping("/index")
    public ResponseEntity<String> indexAllConcerts() {
    int totalChunks = ragIndexService.indexAllConcerts();

    return  ResponseEntity.ok(
            "공연 전체 색인 완료:총 " +totalChunks + "개 청크"
    );
    }
}
