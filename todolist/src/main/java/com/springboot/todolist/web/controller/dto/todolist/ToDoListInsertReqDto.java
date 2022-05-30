package com.springboot.todolist.web.controller.dto.todolist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.springboot.todolist.domain.todolist.ToDoListMst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoListInsertReqDto {
	@NotBlank
	private String content;
	
	public ToDoListMst toToDoListMstEntity(int usercode) {
		return ToDoListMst.builder()
				.usercode(usercode)
				.content(content)
				.build();
	}
	

}
