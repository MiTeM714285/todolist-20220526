package com.springboot.todolist.web.dto.account;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDeleteReqDto {
	
	@NotBlank
	private String username;

}

