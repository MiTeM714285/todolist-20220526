package com.springboot.todolist.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
	
	private int usercode;
	private String username;
	private String oauth2_username;
	private String password;
	private String email;
	private String name;
	private String provider;

}