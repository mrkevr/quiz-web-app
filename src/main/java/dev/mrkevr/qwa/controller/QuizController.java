package dev.mrkevr.qwa.controller;

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
import dev.mrkevr.qwa.dto.UserQuizAnswer;
import dev.mrkevr.qwa.service.CategoryService;
import dev.mrkevr.qwa.service.QuizService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizController {
	
	CategoryService categoryServ;
	QuizService quizServ;
	
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
		
		UserQuizAnswer userQuizAnswer = quizServ.createUserQuizAnswer(map);
		QuizResult quizResult = quizServ.getQuizResult(userQuizAnswer);
		
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("result", quizResult);
		mav.addObject("category", categoryServ.findById(quizResult.getCategoryId()).getName());
		return mav;
	}
	
	
}
