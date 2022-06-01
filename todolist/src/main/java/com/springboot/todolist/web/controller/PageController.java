package com.springboot.todolist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/index")
	public String index() {
		return "todolist/todolist";
	}
	
	@GetMapping("/auth/signin") // 로그인
	public String signin() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup") // 가입
	public String signup() {
		return "auth/signup";
	}
	
	@GetMapping("/account") // 회원정보
	public String account() {
		return "account/account";
	}
}
