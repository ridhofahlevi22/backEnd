package com.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<String> exception(DataNotFoundException exception) {
		return new ResponseEntity<String>("Data not found", HttpStatus.NOT_FOUND);
	}
}
