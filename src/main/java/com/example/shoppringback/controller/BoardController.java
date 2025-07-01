package com.example.shoppringback.controller;

import com.example.shoppringback.entity.QnA;
import com.example.shoppringback.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트와 연결 위해 CORS 허용
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<QnA> getAllBoards() {
        return boardService.findAll();
    }

    @GetMapping("/{id}")
    public QnA getBoard(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @PostMapping
    public QnA createBoard(@RequestBody QnA qnA) {
        return boardService.save(qnA);
    }

    @PutMapping("/{id}")
    public QnA updateBoard(@PathVariable Long id, @RequestBody QnA qnA) {
        return boardService.update(id, qnA);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
    }


}
