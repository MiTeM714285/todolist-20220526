package com.springboot.todolist.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.todolist.handler.ex.CustomValidationApiException;
import com.springboot.todolist.web.controller.dto.CustomResponseDto;


@RestController
@ControllerAdvice // ValidationAdvice.java 등록하여 구체적 로직 처리
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationApiException.class) // 해당 예외 클래스를 자동으로 찾기
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CustomResponseDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	}
}