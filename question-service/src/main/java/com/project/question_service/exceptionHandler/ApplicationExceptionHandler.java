package com.project.question_service.exceptionHandler;

import com.project.question_service.exception.QuestionNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(QuestionNotFound.class)
    public ResponseEntity<Object> employeeNotFoundById(QuestionNotFound exception) {
        Map<String,Object> map = new HashMap<>();
        map.put("status", HttpStatus.NOT_FOUND.value());
        map.put("message", exception.getMessage());
        map.put("root cause", "This exception occurs because employee is not found by the given id");
//        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
