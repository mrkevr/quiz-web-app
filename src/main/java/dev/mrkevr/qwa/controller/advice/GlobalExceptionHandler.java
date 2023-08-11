package dev.mrkevr.qwa.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	// not working
	
	@ExceptionHandler(Exception.class)
	public String handleEmployeeNotFoundException(Exception ex) {
		
		LOGGER.error(ex.getMessage(), ex);
		
		return "error/500";
	}
}
