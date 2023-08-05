package dev.mrkevr.qwa.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.mrkevr.qwa.model.Question;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuestionService {
	
	String BASE_URL = "http://localhost:8085/api/questions";
	RestTemplate restTemplate;
	
	public List<Question> getQuestionsById(List<String> questionIds){
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(questionIds, headers);
        
        ResponseEntity<List<Question>> responseEntity = restTemplate.exchange(
        		BASE_URL,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Question>>() {});

        List<Question> questions = responseEntity.getBody();
		return questions;
	}
	
	public List<Question> getQuestionsQuizId(String quizId){
		
		String uri = BASE_URL+"/quiz/"+quizId;
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        ResponseEntity<List<Question>> response = restTemplate
				.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Question>>() {
				});
        
		return response.getBody();
	}
}
