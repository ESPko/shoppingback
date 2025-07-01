package com.example.shoppringback.controller;

import com.example.shoppringback.entity.QnA;
import com.example.shoppringback.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트와 연결 위해 CORS 허용
public class QnaController {

    private final QnaService qnaService;

    @GetMapping
    public List<QnA> getAllBoards() {
        return qnaService.findAll();
    }

    @GetMapping("/{id}")
    public QnA getBoard(@PathVariable Long id) {
        return qnaService.findById(id);
    }

    @PostMapping
    public QnA createBoard(@RequestBody QnA qnA) {
        return qnaService.save(qnA);
    }

    @PutMapping("/{id}")
    public QnA updateBoard(@PathVariable Long id, @RequestBody QnA qnA) {
        return qnaService.update(id, qnA);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        qnaService.delete(id);
    }


}
