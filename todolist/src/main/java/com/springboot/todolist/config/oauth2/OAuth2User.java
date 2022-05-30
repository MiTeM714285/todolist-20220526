package com.springboot.todolist.config.oauth2;

import lombok.Builder;

@Builder
public class OAuth2User {
	private String oauth2_username;
	private String email;
	private String name;
	private String provider;
}
