package com.springboot.todolist.web.controller.dto.todolist.auth;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignupReqDto {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
}