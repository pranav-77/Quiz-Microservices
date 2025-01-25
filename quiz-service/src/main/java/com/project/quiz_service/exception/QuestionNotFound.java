package com.project.quiz_service.exception;

public class QuestionNotFound extends RuntimeException {
    public QuestionNotFound(String message) {
        super(message);
    }
}
