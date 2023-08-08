package dev.mrkevr.qwa.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import dev.mrkevr.qwa.dto.QuizForm;
import dev.mrkevr.qwa.dto.QuizResult;
import dev.mrkevr.qwa.dto.QuizUserData;
import dev.mrkevr.qwa.dto.UserAnswer;
import dev.mrkevr.qwa.dto.UserQuizAnswer;
import dev.mrkevr.qwa.model.Question;
import dev.mrkevr.qwa.model.Quiz;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuizService {
	
	String BASE_URL = "http://localhost:8085/api/quizzes";
	RestTemplate restTemplate;
	QuestionService questionServ;
	
	public Quiz getRandomQuizByCategoryId(String categoryId) {
		
		String uri = BASE_URL+"/random?categoryId="+categoryId;
		
		ResponseEntity<Quiz> response = restTemplate
				.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<Quiz>() {
				});
		
		return response.getBody();
	}
	
	public QuizForm getQuizForm(QuizUserData quizUserData) {
		
		Quiz quiz = this.getRandomQuizByCategoryId(quizUserData.getCategoryId());
		quizUserData.setQuizId(quiz.getQuizId());
		
		// Fetch the questions by quiz id
		List<Question> questions = questionServ.getQuestionsQuizId(quiz.getQuizId());
		Collections.shuffle(questions);
		
		return QuizForm.builder()
				.quizUserData(quizUserData)
				.questions(questions)
				.build();
	}
	
	public QuizResult getQuizResult(UserQuizAnswer userQuizAnswer) {
		
		String uri = BASE_URL+"/check";
		HttpEntity<UserQuizAnswer> request = new HttpEntity<>(userQuizAnswer);
		ResponseEntity<QuizResult> response = restTemplate.exchange(uri, HttpMethod.POST, request, QuizResult.class);
		return response.getBody();
	}
	
	public UserQuizAnswer createUserQuizAnswer(MultiValueMap<String, String> map) {
		// Create instance of UserQuizAnswer to be sent as request body in the service class
		UserQuizAnswer userQuizAnswer = UserQuizAnswer.builder()
				.username(map.getFirst("quizUserData.username").trim())
				.categoryId(map.getFirst("quizUserData.categoryId"))
				.quizId(map.getFirst("quizUserData.quizId"))
				.userAnswers(new ArrayList<UserAnswer>())
				.build();
		
		// Filter the collection
		map.remove("quizUserData.username");
		map.remove("quizUserData.quizId");
		map.remove("quizUserData.categoryId");
		
		// Each iteration of questionId and answer from the map will be added to the request body's collection of UserAnswer
		map.forEach((id, answer) -> {
			userQuizAnswer.getUserAnswers().add(new UserAnswer(id, answer.get(0)));
		});
		return userQuizAnswer;
	}
}
