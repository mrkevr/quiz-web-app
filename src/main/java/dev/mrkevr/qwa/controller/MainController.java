package dev.mrkevr.qwa.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mrkevr.qwa.dto.QuizResult;
import dev.mrkevr.qwa.dto.QuizUserData;
import dev.mrkevr.qwa.dto.UserAnswer;
import dev.mrkevr.qwa.dto.UserQuizAnswer;
import dev.mrkevr.qwa.service.CategoryService;
import dev.mrkevr.qwa.service.QuestionService;
import dev.mrkevr.qwa.service.QuizService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainController {
	
	CategoryService categoryServ;
	QuestionService questionServ;
	QuizService quizServ;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("categories", categoryServ.getAll());
		mav.addObject("quizUserData", new QuizUserData());
		return mav;
	}
	
	@GetMapping("/quiz")
	public ModelAndView quizForm(
			@Valid @ModelAttribute QuizUserData quizUserData,
			BindingResult result,
			RedirectAttributes redirectAttrs) {
		
		if(result.hasErrors()) {
			ModelAndView mav = new ModelAndView("index");
			mav.addObject("categories", categoryServ.getAll());
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("quiz");
		mav.addObject("quizForm", quizServ.getQuizForm(quizUserData));
		mav.addObject("category", categoryServ.findById(quizUserData.getCategoryId()).getName());
		
		return mav;
	}
	
	@PostMapping("/quiz")
	public ModelAndView processQuizForm(@RequestParam MultiValueMap<String, String> map) {
		
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
		
		QuizResult quizResult = quizServ.getQuizResult(userQuizAnswer);
		
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("result", quizResult);
		mav.addObject("category", categoryServ.findById(quizResult.getCategoryId()).getName());
		return mav;
	}
	
	@GetMapping("/ranking")
	public String score() {
		
		
		
		
		return "ranking.html";
	}
}
