package dev.mrkevr.qwa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.qwa.dto.QuizUserData;
import dev.mrkevr.qwa.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainController {
	
	CategoryService categoryServ;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("categories", categoryServ.getAll());
		mav.addObject("quizUserData", new QuizUserData());
		return mav;
	}
	
	@GetMapping("/ranking")
	public ModelAndView ranking(
			@RequestParam(name = "categoryId", required = false, defaultValue = "null") String categoryId) {
		
		ModelAndView mav = new ModelAndView("ranking");
		mav.addObject("categories", categoryServ.getAll());
		
		
		return mav;
	}
}
