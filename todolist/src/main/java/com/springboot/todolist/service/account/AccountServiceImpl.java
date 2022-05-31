package com.springboot.todolist.service.account;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.todolist.domain.user.User;
import com.springboot.todolist.domain.user.UserRepository;
import com.springboot.todolist.web.dto.account.AccountUpdateReqDto;
import com.springboot.todolist.web.dto.auth.PasswordCheckReqDto;
import com.springboot.todolist.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	private final UserRepository userRepository;

	@Override
	public boolean updateUserByUsername(AccountUpdateReqDto accountUpdateReqDto) throws Exception {
		if(accountUpdateReqDto.getPassword().equals("") || accountUpdateReqDto.getPassword() == null) { // 비밀번호 제외하고 업데이트시
			return userRepository.updateUserByUsernameWithoutPassword(accountUpdateReqDto.toUserEntity()) > 0;
		} else { // 비밀번호 포함 업데이트시
			return userRepository.updateUserByUsername(accountUpdateReqDto.toUserEntity()) > 0;
		}
		
	}

	@Override
	public boolean deleteUserByUsername(String username) throws Exception {
		// TODO Auto-generated method stub
		return userRepository.deleteUserByUsername(username) > 0;
	}

	@Override
	public boolean checkPassword(PasswordCheckReqDto passwordCheckReqDto) throws Exception {
		String currentPassword = userRepository.findPasswordByUsername(passwordCheckReqDto.getUsername());
		
		boolean result = new BCryptPasswordEncoder().matches(passwordCheckReqDto.getPassword(), currentPassword);
		System.out.println(result);
		return result;
	}

}
