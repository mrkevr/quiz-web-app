package dev.mrkevr.qwa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public ModelAndView quizFormGet(
			@Valid @ModelAttribute QuizUserData quizUserData,
			BindingResult result) {
		
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
	public ModelAndView quizFormPost(
			@RequestParam MultiValueMap<String, String> map,
			RedirectAttributes redirectattrs) {
		
		UserQuizAnswer userQuizAnswer = quizServ.createUserQuizAnswer(map);
		QuizResult quizResult = quizServ.getQuizResult(userQuizAnswer);
		
		redirectattrs.addFlashAttribute("result", quizResult);
		redirectattrs.addFlashAttribute("category", categoryServ.findById(quizResult.getCategoryId()).getName());
		return  new ModelAndView("redirect:/result");
	}
	
	@GetMapping("/result")
	public ModelAndView result() {
		return new ModelAndView("result");
	}
}
