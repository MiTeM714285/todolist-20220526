package com.springboot.todolist.service.todolist;

import java.util.List;

import com.springboot.todolist.web.controller.dto.todolist.ToDoListInsertReqDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListRespDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListUpdateReqDto;

public interface ToDoListService {
	
	public int addToDo(ToDoListInsertReqDto toDoListInsertReqDto) throws Exception;
	public ToDoListRespDto getToDo(int id) throws Exception;
	public List<ToDoListRespDto> getListAll() throws Exception;
	public int getIsUndoneCount() throws Exception;
	public int modifyTodo(int id, ToDoListUpdateReqDto toDoListUpdateReqDto) throws Exception;
	public int modifyIsDone(int id) throws Exception;
	public int modifyIsUnDone(int id) throws Exception;
	public int removeTodo(int id) throws Exception;
}
