package com.example.shoppringback.service;

import com.example.shoppringback.entity.Board;
import com.example.shoppringback.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public Board update(Long id, Board updatedBoard) {
        Board existing = findById(id);
        existing.setTitle(updatedBoard.getTitle());
        existing.setProduct(updatedBoard.getProduct());
        existing.setName(updatedBoard.getName());
        existing.setDate(updatedBoard.getDate());
        existing.setHit(updatedBoard.getHit());
        return boardRepository.save(existing);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
