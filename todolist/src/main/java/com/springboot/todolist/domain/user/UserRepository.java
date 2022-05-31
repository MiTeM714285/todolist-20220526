package com.springboot.todolist.domain.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
	
	public int save(User user) throws Exception;
	public User findUserByUsername(String username) throws Exception;
	public User findOAuth2UserByOAuth2Username(String oAuth2Username) throws Exception;
	public int updateUserByUsername(User user) throws Exception;
	public int updateUserByUsernameWithoutPassword(User user) throws Exception;
	public int deleteUserByUsername(String username) throws Exception;
	public String findPasswordByUsername(String username) throws Exception;

}
