package com.human.mini_prj.controller;

import com.human.mini_prj.dto.BoardResDto;
import com.human.mini_prj.dto.BoardWriteReqDto;
import com.human.mini_prj.entity.Board;
import com.human.mini_prj.entity.Member;
import com.human.mini_prj.repository.BoardRepository;
import com.human.mini_prj.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j      // log 메시지 출력
@RestController  // Restfull  API 를 지원하는 컨트롤러
@RequestMapping("/board")       // http://localhost:8111/board
@RequiredArgsConstructor        // 생성자를 통한 의존성 주입을 자동 생성
public class BoardController {
    private  final BoardService boardService; // 의존성 주입

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardResDto>> boardList() {
        List<BoardResDto> list = boardService.getBoardList();
        return ResponseEntity.ok(list);
    }
    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResDto> boardDetail(@PathVariable Long id) {
        BoardResDto boardResDto = boardService.getBoardDetail(id);
        return ResponseEntity.ok(boardResDto);
    }

    // 게시글 작성
    @PostMapping("/write")
    public  ResponseEntity<Boolean> boardWrite(@RequestBody BoardWriteReqDto dto) {
        boolean result = boardService.writeBoard(dto);
        return ResponseEntity.ok(result);

    }
    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> boardModify(@PathVariable Long id, @RequestBody BoardWriteReqDto dto) {
        boolean result = boardService.updateBoard(id, dto);
        return ResponseEntity.ok(result);
    }
    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean>boardDelete(@PathVariable Long id){
        boolean result = boardService.deleteBoard(id);
        return ResponseEntity.ok(result);
    }

}
