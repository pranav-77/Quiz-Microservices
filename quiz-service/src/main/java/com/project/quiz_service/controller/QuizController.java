package com.project.quiz_service.controller;

import com.project.quiz_service.dto.QuizDto;
import com.project.quiz_service.model.Responses;
import com.project.quiz_service.dto.QuestionWrapper;
import com.project.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody QuizDto quizDto) {
        return quizService.create(quizDto.getCategory(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<List<QuestionWrapper>> getById(@PathVariable Integer id) {
        return quizService.getById(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submit(@PathVariable Integer id, @RequestBody List<Responses> responses) {
        return quizService.submit(id, responses);
    }
}
