package com.springboot.todolist.web.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.todolist.service.todolist.ToDoListService;
import com.springboot.todolist.web.controller.dto.CustomResponseDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListInsertReqDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListRespDto;
import com.springboot.todolist.web.controller.dto.todolist.ToDoListUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ToDoListController {
	
	private final ToDoListService toDoListService;
	
	@GetMapping("/todo/list") // 리스트 전체 들고오기
	public ResponseEntity<?> getListAll() throws Exception {
		List<ToDoListRespDto> toDoListRespDtos = toDoListService.getListAll();
		return new ResponseEntity<>(new CustomResponseDto<List<ToDoListRespDto>>(1, "ToDoList 목록 로드" , toDoListRespDtos), HttpStatus.OK);
	}
	
	@GetMapping("/todo/{id}") // id기준으로 하나 조회
	public ResponseEntity<?> getToDo(@PathVariable int id) throws Exception {
		ToDoListRespDto toDoListRespDtos = toDoListService.getToDo(id);
		return new ResponseEntity<>(new CustomResponseDto<ToDoListRespDto>(1, "ToDoList 한개 로드" , toDoListRespDtos), HttpStatus.OK);
	}
	
	@GetMapping("/todo/isUndoneCount")
	public ResponseEntity<?> getIsUndoneCount() throws Exception {
		int isUndoneCount = toDoListService.getIsUndoneCount();
		return new ResponseEntity<>(new CustomResponseDto<Integer>(1, "isUndoneCount 갯수 로드" , isUndoneCount), HttpStatus.OK);
	}
	
	@PostMapping("/todo") // 내용 추가
	public ResponseEntity<?> addToDo(@Valid @RequestBody ToDoListInsertReqDto toDoListInsertReqDto, BindingResult bindingResult) throws Exception {
		int id = toDoListService.addToDo(toDoListInsertReqDto);
		return new ResponseEntity<>(new CustomResponseDto<Integer>(1, "글 작성 완료", id), HttpStatus.OK);
	}
	
	@PutMapping("/todo/{id}") // 내용 수정
	public ResponseEntity<?> modifyTodo(@PathVariable int id, @Valid @RequestBody ToDoListUpdateReqDto toDoListUpdateReqDto, BindingResult bindingResult) throws Exception {
		int resultId = toDoListService.modifyTodo(id, toDoListUpdateReqDto);
		if (resultId > 0) {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(1, "글 수정 완료(수정된 글 id↓)", resultId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(-1, "글 수정 미실행(id없음)", resultId), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/todo/isdone/{id}") // 할일 완료
	public ResponseEntity<?> modifyIsDone(@PathVariable int id) throws Exception {
		int resultId = toDoListService.modifyIsDone(id);
		if (resultId > 0) {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(1, "할일 완료(수정된 글 id↓)", resultId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(-1, "할일 완료 미실행(id없음)", resultId), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/todo/isundone/{id}") // 할일 완료
	public ResponseEntity<?> modifyIsUnDone(@PathVariable int id) throws Exception {
		int resultId = toDoListService.modifyIsUnDone(id);
		if (resultId > 0) {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(1, "할일 완료(수정된 글 id↓)", resultId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(-1, "할일 완료 미실행(id없음)", resultId), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<?> removeTodo(@PathVariable int id) throws Exception {
		int resultId = toDoListService.removeTodo(id);
		if (resultId > 0) {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(1, "글 삭제 완료(삭제된 글 id↓)", resultId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomResponseDto<Integer>(-1, "글 삭제 미실행(id없음)", resultId), HttpStatus.BAD_REQUEST);
		}
	}
}