package com.springboot.todolist.service.todolist;

import java.util.List;

import com.springboot.todolist.web.controller.dto.todolist.ToDoListInsertReqDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListRespDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListUpdateReqDto;

public interface ToDoListService {
	
	public int addToDo(int usercode, ToDoListInsertReqDto toDoListInsertReqDto) throws Exception;
	public ToDoListRespDto getToDo(int id) throws Exception;
	public List<ToDoListRespDto> getListAll(int usercode) throws Exception;
	public int getIsUndoneCount(int usercode) throws Exception;
	public int modifyTodo(int id, ToDoListUpdateReqDto toDoListUpdateReqDto) throws Exception;
	public int modifyIsDone(int id) throws Exception;
	public int modifyIsUnDone(int id) throws Exception;
	public int removeTodo(int id) throws Exception;
}
