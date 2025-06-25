package com.example.shoppringback.controller;

import com.example.shoppringback.entity.Board;
import com.example.shoppringback.repository.BoardRepository;
import com.example.shoppringback.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트와 연결 위해 CORS 허용
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.findAll();
    }

    @GetMapping("/{id}")
    public Board getBoard(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardService.save(board);
    }

    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board board) {
        return boardService.update(id, board);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
    }
}
