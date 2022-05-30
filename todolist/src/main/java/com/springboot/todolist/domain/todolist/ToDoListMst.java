package com.springboot.todolist.domain.todolist;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToDoListMst {
	private int id;
	private int usercode;
	private String content;
	private int isdone;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
}
