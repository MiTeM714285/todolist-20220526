package com.springboot.todolist.service.todolist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.todolist.domain.todolist.ToDoListMst;
import com.springboot.todolist.domain.todolist.ToDoListRepository;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListInsertReqDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListRespDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListUpdateReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoListServiceImpl implements ToDoListService{
	
	private final ToDoListRepository toDoListRepository;

	@Override
	public int addToDo(int usercode, ToDoListInsertReqDto toDoListInsertReqDto) throws Exception{
		ToDoListMst toDoListMst = toDoListInsertReqDto.toToDoListMstEntity(usercode);
		int result = toDoListRepository.addToDo(toDoListMst);
		if(result > 0) {
			return toDoListMst.getId();
		}
		return 0;
	}

	@Override
	public List<ToDoListRespDto> getListAll(int usercode) throws Exception {
		List<ToDoListRespDto> toDoListRespDtos = new ArrayList<ToDoListRespDto>();
		List<ToDoListMst> toDoListListAll = toDoListRepository.getListAll(usercode);
		for(ToDoListMst element : toDoListListAll) {
			toDoListRespDtos.add(ToDoListRespDto.builder()
					.id(element.getId())
					.content(element.getContent())
					.isdone(element.getIsdone())
					.create_date(element.getCreate_date())
					.update_date(element.getUpdate_date())
					.build());
		}
		return toDoListRespDtos;
	}
	
	@Override
	public int getIsUndoneCount(int usercode) throws Exception {
		// TODO Auto-generated method stub
		return toDoListRepository.getIsUndoneCount(usercode);
	}
	
	@Override
	public ToDoListRespDto getToDo(int id) throws Exception {
		ToDoListMst toDoListMst = toDoListRepository.getToDo(id);
		ToDoListRespDto toDoListRespDto = ToDoListRespDto.builder()
				.id(toDoListMst.getId())
				.content(toDoListMst.getContent())
				.isdone(toDoListMst.getIsdone())
				.create_date(toDoListMst.getCreate_date())
				.update_date(toDoListMst.getUpdate_date())
				.build();
		return toDoListRespDto;
	}

	@Override
	public int modifyTodo(int id, ToDoListUpdateReqDto toDoListUpdateReqDto) throws Exception {
		ToDoListMst toDoListMst = toDoListUpdateReqDto.toToDoListMstEntity(id);
		return toDoListRepository.modifyTodo(toDoListMst) > 0 ? id : 0;
	}
	
	@Override
	public int modifyIsDone(int id) throws Exception {
		return toDoListRepository.modifyIsDone(id) > 0 ? id : 0;
	}
	
	@Override
	public int modifyIsUnDone(int id) throws Exception {
		return toDoListRepository.modifyIsUnDone(id) > 0 ? id : 0;
	}

	@Override
	public int removeTodo(int id) throws Exception {
		return toDoListRepository.removeTodo(id) > 0 ? id : 0;
	}






}
