package dev.mrkevr.qwa.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
	
	public Ranking getByCategoryId(String categoryId) {
		
		String uri = BASE_URL+"/category/"+categoryId;
		
		ResponseEntity<Ranking> response = restTemplate.exchange(
				uri, 
				HttpMethod.GET, 
				null,
				new ParameterizedTypeReference<Ranking>() {
				});
		
		Ranking ranking = response.getBody();
		
		// Sort the map in descending order based on percentage values
        Map<String, Double> sortedMap = ranking.getUsernamePercentage().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        
        ranking.setUsernamePercentage(sortedMap);
        
		return ranking;
	}
}
