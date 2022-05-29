package com.springboot.todolist.domain.todolist;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ToDoListRepository {
	
	public int addToDo(ToDoListMst todoListMst);
	public ToDoListMst getToDo(int id);
	public List<ToDoListMst> getListAll();
	public int getIsUndoneCount();
	public int modifyTodo(ToDoListMst todoListMst);
	public int modifyIsDone(int id);
	public int modifyIsUnDone(int id);
	public int removeTodo(int id);

}
