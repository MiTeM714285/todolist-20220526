package com.springboot.todolist.service.auth;

import org.springframework.stereotype.Service;

import com.springboot.todolist.domain.user.User;
import com.springboot.todolist.domain.user.UserRepository;
import com.springboot.todolist.web.dto.auth.SignupReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;

	@Override
	public boolean signup(SignupReqDto signupReqDto) throws Exception{
		return userRepository.save(signupReqDto.toEntity()) > 0 ? true : false;
	}

	@Override
	public boolean checkUsername(String username) throws Exception {
		return userRepository.findUserByUsername(username) == null ? true : false;
	}
	
	@Override
	public User findUserByUsername(String username) throws Exception {
		return userRepository.findUserByUsername(username);
	}
}
