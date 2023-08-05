package dev.mrkevr.qwa.dto;

import java.util.List;

import dev.mrkevr.qwa.model.Question;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizForm {
	
	QuizUserData quizUserData;
	
	List<Question> questions;
}
