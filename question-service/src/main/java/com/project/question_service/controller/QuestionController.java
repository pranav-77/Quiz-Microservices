package com.project.question_service.controller;

import com.project.question_service.enumeration.Level;
import com.project.question_service.model.Responses;
import com.project.question_service.requestDto.QuestionRequestDto;
import com.project.question_service.responseDto.QuestionResponseDto;
import com.project.question_service.responseDto.QuestionWrapper;
import com.project.question_service.service.QuestionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
@Slf4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("test")
    public String message() {
        return "Welcome";
    }

    @PostMapping("add")
    public ResponseEntity<List<QuestionResponseDto>> add(@RequestBody @Valid List<QuestionRequestDto> requestDto) {
        List<QuestionResponseDto> responseDto = questionService.add(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<QuestionResponseDto>> getAll() {
        List<QuestionResponseDto> responseDto = questionService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<QuestionResponseDto> getById(@PathVariable Integer id)  {
        QuestionResponseDto responseDto = questionService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("getByCategory")
    public ResponseEntity<List<QuestionResponseDto>> getByCategory(@RequestParam String category) {
        List<QuestionResponseDto> responseDto = questionService.getByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("getByDifficulty")
    public ResponseEntity<List<QuestionResponseDto>> getByLevel(@RequestParam String category, @RequestParam Level difficultyLevel) {
        List<QuestionResponseDto> responseDto = questionService.getByLevel(category, difficultyLevel);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        return questionService.delete(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<QuestionResponseDto> update(@PathVariable Integer id, @RequestBody @Valid QuestionRequestDto requestDto) {
        QuestionResponseDto responseDto = questionService.update(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("generateQuestions")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.getQuestionsForQuiz(category,numQ));
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        log.info(environment.getProperty("local.server.port"));
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionsFromId(questionIds));
    }

    @PostMapping("calculate")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Responses> responses) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.calculateScore(responses));
    }
}
