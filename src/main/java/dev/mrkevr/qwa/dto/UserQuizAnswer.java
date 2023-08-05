package dev.mrkevr.qwa.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserQuizAnswer {
	
	String username;
	String categoryId;
	String quizId;
	List<UserAnswer> userAnswers;
}
