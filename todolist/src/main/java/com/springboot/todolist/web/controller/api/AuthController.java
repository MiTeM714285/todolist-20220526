package com.springboot.todolist.web.controller.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.todolist.config.auth.PrincipalDetails;
import com.springboot.todolist.service.auth.AuthService;
import com.springboot.todolist.web.dto.CustomResponseDto;
import com.springboot.todolist.web.dto.auth.SignupReqDto;
import com.springboot.todolist.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder; //SecurityConfig의 BCryptPasswordEncoder
	private final AuthService authService;
	
	@GetMapping("/authentication")
	public ResponseEntity<?> getAuthentication(@AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		System.out.println(principalDetails.getUser().getUsercode());
		String password = principalDetails.getUser().getPassword();
		System.out.println(bCryptPasswordEncoder.matches("1234", password));
		return new ResponseEntity<>(new CustomResponseDto<PrincipalDetails>(1, "세션정보",principalDetails), HttpStatus.OK);
	}
	
	@GetMapping("/signup/username")
	public ResponseEntity<?> checkUsername(@Valid UsernameCheckReqDto usernameCheckReqDto, BindingResult bindingResult) throws Exception {
		boolean result = authService.checkUsername(usernameCheckReqDto.getUsername());
		
		if(result) {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(1, "해당 아이디 사용가능", result), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(-1, "중복된 아이디입니다.", result), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult) throws Exception {
		boolean result = authService.signup(signupReqDto);
		
		if(result) {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(1, "회원가입 완료", result), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(-1, "회원가입 실패", result), HttpStatus.BAD_REQUEST);
		}
		
	}
}