package com.project.question_service.mapper;

import com.project.question_service.model.Question;
import com.project.question_service.requestDto.QuestionRequestDto;
import com.project.question_service.responseDto.QuestionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question mapToEntity(QuestionRequestDto requestDto) {
        Question question = new Question();
        question.setQuestionTitle(requestDto.getQuestionTitle());
        question.setCategory(requestDto.getCategory());
        question.setOption1(requestDto.getOption1());
        question.setOption2(requestDto.getOption2());
        question.setOption3(requestDto.getOption3());
        question.setOption4(requestDto.getOption4());
        question.setRightAnswer(requestDto.getRightAnswer());
        question.setDifficultyLevel(requestDto.getDifficultyLevel());

        return question;
    }

    public QuestionResponseDto mapToResponse(Question question) {
        QuestionResponseDto responseDto = new QuestionResponseDto();
        responseDto.setId(question.getId());
        responseDto.setQuestionTitle(question.getQuestionTitle());
        responseDto.setCategory(question.getCategory());
        responseDto.setOption1(question.getOption1());
        responseDto.setOption2(question.getOption2());
        responseDto.setOption3(question.getOption3());
        responseDto.setOption4(question.getOption4());
        responseDto.setRightAnswer(question.getRightAnswer());
        responseDto.setDifficultyLevel(question.getDifficultyLevel());

        return responseDto;
    }
}

