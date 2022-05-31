package com.springboot.todolist.web.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.todolist.domain.user.User;

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
	@Email
	private String email;
	
	public User toEntity() {
		return User.builder()
			.username(username)
			.password(new BCryptPasswordEncoder().encode(password))
			.name(name)
			.email(email)
			.build();
	}
}