package com.project.quiz_service.service;

import com.project.quiz_service.exception.QuizNotFound;
import com.project.quiz_service.feign.QuizInterface;
import com.project.quiz_service.model.Quiz;
import com.project.quiz_service.model.Responses;
import com.project.quiz_service.repository.QuizRepository;
import com.project.quiz_service.dto.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizInterface quizInterface;


    public ResponseEntity<String> create(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepository.save(quiz);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getById(Integer id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFound("Not Found"));
        List<Integer> questionIds = quiz.getQuestions();
        ResponseEntity<List<QuestionWrapper>> questionsForUser = quizInterface.getQuestionsFromId(questionIds);
        return questionsForUser;
    }

    public ResponseEntity<Integer> submit(Integer id, List<Responses> responses) {
        ResponseEntity<Integer> score = quizInterface.calculateScore(responses);
        return score;
    }
}
