package dev.mrkevr.qwa.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuizUserData {
	
	@Length(min = 6, max = 36, message = "*Must be 6-36 characters long")
	private String username;
	
	@NotBlank(message = "*Please select category")
	private String categoryId;
	
	private String quizId;
	
	private int score;
}
