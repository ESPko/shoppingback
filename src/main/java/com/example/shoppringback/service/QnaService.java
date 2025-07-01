package com.example.shoppringback.service;

import com.example.shoppringback.entity.QnA;
import com.example.shoppringback.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    public List<QnA> findAll() {
        return qnaRepository.findAll();
    }

    public QnA findById(Long id) {
        return qnaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
    }

    public QnA save(QnA qnA) {
        return qnaRepository.save(qnA);
    }

    public QnA update(Long id, QnA updatedQnA) {
        QnA existing = findById(id);
        existing.setTitle(updatedQnA.getTitle());
        existing.setProduct(updatedQnA.getProduct());
        existing.setName(updatedQnA.getName());
        existing.setDate(updatedQnA.getDate());
        existing.setHit(updatedQnA.getHit());
        return qnaRepository.save(existing);
    }

    public void delete(Long id) {
        qnaRepository.deleteById(id);
    }
}
