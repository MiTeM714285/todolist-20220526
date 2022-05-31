package com.springboot.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.todolist.config.oauth2.PrincipalOAuth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity // 기존의 WebSecurityConfigurerAdapter의 설정을 비활성화하고, 현재 클래스(SecurityConfig)의 설정을 따르게함
@Configuration // 이 객체가 Configuration 관련 설정 객체, @Bean어노테이션을 쓸 수 있게됨
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PrincipalOAuth2UserService principalOAuth2UserService;
	
	@Bean(name = "BCrypt")
	public BCryptPasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder(); // IoC에 등록 (굳이 Class를 따로 만들어 extends 하여 쓸 필요없음)
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // 로그인 시 입력란에 csrf토큰이 각각 있는데, 이 토큰을 먼저 검사, 이는 위조된 사이트나 postman같은 앱에서 요청시 응답을 방지
		http.authorizeRequests() // 인증관련 요청
		//.antMatchers("/index") // 해당 등록된 uri는 인증을 거치도록
		.antMatchers("/index", "/account", "/api/v1/todo/**","/api/v1/account/**") // /auth로 시작되는 요청은
		.authenticated() // 인증 거치기
		.anyRequest() // 나머지 요청들은
		.permitAll() // 인증 필요없음
		.and()
		.formLogin() // 파라미터를 받아서 로그인
		.loginPage("/auth/signin") // 로그인 페이지 get요청(view)
		.loginProcessingUrl("/auth/signin") // 로그인 post요청(PrincipalDetailsService -> loadUserByUsername() 호출)
		.defaultSuccessUrl("/index") // 로그인성공시
		.failureUrl("/api/v1/auth/signin/fail")
		.and()
		.oauth2Login() // oauth2 로그인 사용
		.loginPage("/auth/signin")
		.userInfoEndpoint()
		/*
		 * 1. 코드를 받는다(google, naver, kakao 등 로그인 요청을 했을 시 부여되는 코드번호)
		 * 2. Access 토큰 발급(JWT)
		 * 3. 스코프(범위)정보에 접근할 수 있는 권한 발급
		 * 4. 해당 정보를 Security에서 활용
		 */
		.userService(principalOAuth2UserService) // 해당 서비스 클래스
		.and()
		.defaultSuccessUrl("/index"); // 로그인성공시
	}
}
