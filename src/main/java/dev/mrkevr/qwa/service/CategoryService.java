package dev.mrkevr.qwa.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.mrkevr.qwa.model.Category;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoryService {
	
	String BASE_URL = "http://localhost:8085/api/categories";
	
	RestTemplate restTemplate;
	
	public List<Category> getAll() {

		ResponseEntity<List<Category>> response = restTemplate
				.exchange(BASE_URL, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Category>>() {
				});
		
		return response.getBody();
	}
	
	public Category findById(String cateogryId) {
		
		String uri = BASE_URL+"/"+cateogryId;
		
		ResponseEntity<Category> response = restTemplate
				.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<Category>() {
				});
		
		return response.getBody();
	}
}
