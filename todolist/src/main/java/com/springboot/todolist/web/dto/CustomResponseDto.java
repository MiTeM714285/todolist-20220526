package com.springboot.todolist.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomResponseDto<T> {
	
	/*
	 *  1 또는 -1를 반환
	 *  1 : 성공, -1 : 실패
	 */
	private int code;

	/*
	 * 응답 메세지 내용
	 */
	private String msg;
	
	/*
	 * 응답 데이터
	 */
	private T data;
	
}
