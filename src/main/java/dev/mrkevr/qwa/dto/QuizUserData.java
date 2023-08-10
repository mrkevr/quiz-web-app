package dev.mrkevr.qwa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizUserData {

	@Pattern(regexp = "^(?=.{5,10}$)[a-zA-Z]+$", message = "*Must be A-Z and 5-10 long")
	String username;

	@NotBlank(message = "*Please select category")
	String categoryId;

	String quizId;

	int score;
}
