package dev.mrkevr.qwa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.qwa.service.CategoryService;
import dev.mrkevr.qwa.service.RankingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RankingController {

	RankingService rankingServ;
	CategoryService categoryServ;

	@GetMapping("/ranking")
	public ModelAndView ranking(
			@RequestParam(name = "categoryId", required = true, defaultValue = "64c235cc7b226d7b9ac5be08") String categoryId) {
		
		ModelAndView mav = new ModelAndView("ranking");
		mav.addObject("categories", categoryServ.getAll());
		mav.addObject("ranking", rankingServ.getByCategoryId(categoryId));
		return mav;
	}
}
