package com.springboot.todolist.service.account;

import com.springboot.todolist.web.dto.account.AccountUpdateReqDto;
import com.springboot.todolist.web.dto.auth.PasswordCheckReqDto;

public interface AccountService {
	
	public boolean updateUserByUsername(AccountUpdateReqDto accountUpdateReqDto) throws Exception;
	public boolean deleteUserByUsername(String username) throws Exception;
	public boolean checkPassword(PasswordCheckReqDto passwordCheckReqDto) throws Exception;
}
