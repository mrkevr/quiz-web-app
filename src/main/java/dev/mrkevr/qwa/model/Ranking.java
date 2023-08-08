package dev.mrkevr.qwa.model;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ranking {
	
	String rankingId;
	
	String categoryId;
	
	String categoryName;
	
	Map<String, Double> usernamePercentage;
}
