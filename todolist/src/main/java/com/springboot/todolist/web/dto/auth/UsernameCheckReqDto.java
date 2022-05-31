package com.springboot.todolist.web.dto.auth;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsernameCheckReqDto {
	@NotBlank
	private String username;
}
