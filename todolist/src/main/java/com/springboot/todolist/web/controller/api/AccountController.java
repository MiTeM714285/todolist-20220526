package com.springboot.todolist.web.controller.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.todolist.service.account.AccountService;
import com.springboot.todolist.web.dto.CustomResponseDto;
import com.springboot.todolist.web.dto.account.AccountDeleteReqDto;
import com.springboot.todolist.web.dto.account.AccountUpdateReqDto;
import com.springboot.todolist.web.dto.auth.PasswordCheckReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
	
	private final AccountService accountService;
	
	@PostMapping("/checkpassword")
	public ResponseEntity<?> checkPassword(@Valid @RequestBody PasswordCheckReqDto passwordCheckReqDto, BindingResult bindingResult) throws Exception {
		boolean result = accountService.checkPassword(passwordCheckReqDto);
		if(result) {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(1, "비밀번호 일치", result), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(-1, "비밀번호 불일치", result), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUserByUsername(@Valid @RequestBody AccountUpdateReqDto accountUpdateReqDto, BindingResult bindingResult) throws Exception {
		boolean result = accountService.updateUserByUsername(accountUpdateReqDto);
		if(result) {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(1, "회원정보 수정완료", result), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(-1, "회원정보 수정실패", result), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUserByUsername(@Valid @RequestBody AccountDeleteReqDto accountDeleteReqDto, BindingResult bindingResult) throws Exception {
		boolean result = accountService.deleteUserByUsername(accountDeleteReqDto.getUsername());
		if(result) {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(1, "회원탈퇴 완료", result), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Boolean>(-1, "회원탈퇴 실패", result), HttpStatus.BAD_REQUEST);
		}
	}
}
