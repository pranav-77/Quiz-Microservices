package com.project.question_service.requestDto;

import com.project.question_service.enumeration.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDto {
    @NotBlank(message = "CATEGORY IS MANDATORY")
    private String category;
    @NotBlank(message = "QUESTION IS MANDATORY")
    private String questionTitle;
    @NotBlank(message = "OPTION1 IS MANDATORY")
    private String option1;
    @NotBlank(message = "OPTION2 IS MANDATORY")
    private String option2;
    @NotBlank(message = "OPTION3 IS MANDATORY")
    private String option3;
    @NotBlank(message = "OPTION4 IS MANDATORY")
    private String option4;
    @NotBlank(message = "ANSWER IS MANDATORY")
    private String rightAnswer;
    @NotNull(message = "Role is mandatory 1.EASY 2.MEDIUM 3.HARD")
    private Level difficultyLevel;
}
