package com.project.quiz_service.exception;

public class QuizNotFound extends RuntimeException {
    public QuizNotFound(String message) {
        super(message);
    }
}
