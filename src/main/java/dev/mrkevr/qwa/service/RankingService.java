package dev.mrkevr.qwa.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.mrkevr.qwa.model.Ranking;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RankingService {
	
	String BASE_URL = "http://localhost:8085/api/rankings";
	RestTemplate restTemplate;
	
	public Ranking getRankingByCategoryId(String categoryId) {
		
		String uri = BASE_URL+"/"+categoryId;
		
		
		
		
		
		return null;
	}
	
	
}
