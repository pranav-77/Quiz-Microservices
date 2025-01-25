package com.project.question_service.service;

import com.project.question_service.enumeration.Level;
import com.project.question_service.exception.QuestionNotFound;
import com.project.question_service.mapper.QuestionMapper;
import com.project.question_service.model.Question;
import com.project.question_service.model.Responses;
import com.project.question_service.repository.QuestionRepository;
import com.project.question_service.requestDto.QuestionRequestDto;
import com.project.question_service.responseDto.QuestionResponseDto;
import com.project.question_service.responseDto.QuestionWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper mapper;

    public List<QuestionResponseDto> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(mapper::mapToResponse)
                .toList();
    }

    public List<QuestionResponseDto> add(List<QuestionRequestDto> requestDto) {
        List<Question> questions = requestDto.stream()
                .map(mapper::mapToEntity)
                .toList();

        List<Question> saved = questionRepository.saveAll(questions);

        return saved.stream()
                .map(mapper::mapToResponse)
                .toList();
    }

    public List<QuestionResponseDto> getByCategory(String category) {
        return questionRepository.findByCategory(category)
                .stream()
                .map(mapper::mapToResponse)
                .toList();
    }

    public List<QuestionResponseDto> getByLevel(String category, Level difficultyLevel) {
        return questionRepository.findByCategoryAndDifficultyLevel(category, difficultyLevel)
                .stream()
                .map(mapper::mapToResponse)
                .toList();
    }

    public QuestionResponseDto getById(Integer id) {
        return questionRepository.findById(id)
                .map(mapper::mapToResponse)
                .orElseThrow(() -> new QuestionNotFound("Not Found"));
    }

    public String delete(Integer id) {
        questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found"));
        questionRepository.deleteById(id);
        return "Success";
    }

    public QuestionResponseDto update(Integer id, @Valid QuestionRequestDto requestDto) {
        getById(id);
        Question question = mapper.mapToEntity(requestDto);
        question.setId(id);
        return mapper.mapToResponse(questionRepository.save(question));
    }

    public List<Integer> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);
        return questions;
    }

    public List<QuestionWrapper> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for (Integer id : questionIds) {
            questions.add(questionRepository.findById(id)
                    .orElseThrow(() -> new QuestionNotFound("Not Found")));
        }

        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return wrappers;
    }

    public Integer calculateScore(List<Responses> responses) {
        int right = 0;
        for (Responses response : responses) {
            Question question = questionRepository.findById(response.getId()).orElseThrow(() -> new QuestionNotFound("Not Found"));
            if (response.getResponse().equalsIgnoreCase(question.getRightAnswer()))
                right++;
        }
        return right;
    }
}
