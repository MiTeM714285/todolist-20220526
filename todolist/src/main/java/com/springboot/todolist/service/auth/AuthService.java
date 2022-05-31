package com.springboot.todolist.service.auth;

import com.springboot.todolist.web.dto.auth.SignupReqDto;

public interface AuthService {
	public boolean signup(SignupReqDto signupReqDto) throws Exception;
	public boolean checkUsername(String username) throws Exception;
	
}
