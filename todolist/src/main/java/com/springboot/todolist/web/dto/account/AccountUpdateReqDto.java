package com.springboot.todolist.web.dto.account;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.todolist.domain.user.User;

import lombok.Data;

@Data
public class AccountUpdateReqDto {
	
	@NotBlank
	private String username;
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	public User toUserEntity() {
		if (password.equals("") || password == null ) {
			return User.builder()
					.username(username)
					.password(null)
					.email(email)
					.name(name)
					.build();
		} else {
			return User.builder()
					.username(username)
					.password(new BCryptPasswordEncoder().encode(password))
					.email(email)
					.name(name)
					.build();
		}
	}
}

