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
public class ToDoListUpdateReqDto {
	@NotNull // int형식은 @NotBlank 사용불가
	private int id;
	@NotBlank
	private String content;
	public ToDoListMst toToDoListMstEntity(int id) {
		return ToDoListMst.builder()
				.id(id)
				.content(content)
				.build();
	}
}
