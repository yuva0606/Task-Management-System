package com.yuva.app.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuva.app.dto.TaskDto;
import com.yuva.app.entities.Task;
import com.yuva.app.service.TaskService;
import com.yuva.app.utils.TaskPageResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		super();
		this.taskService = taskService;
	}

	// to create a new task
	@PostMapping("")
	public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto, HttpServletRequest request) throws ParseException {
		return ResponseEntity.ok(taskService.createTask(taskDto, request));
	}

	// to fetch a specific task by taskId
	@GetMapping("/{id}")
	public ResponseEntity<TaskDto> getTaskById(@PathVariable int id, HttpServletRequest request) {
		return ResponseEntity.ok(taskService.getTaskById(id, request));
	}

	// to fetch all tasks
	@GetMapping("")
	public ResponseEntity<List<TaskDto>> getAllTasks(HttpServletRequest request) {
		return ResponseEntity.ok(taskService.getAllTasks(request));
	}

	// to update a specific task
	@PutMapping("")
	public ResponseEntity<Task> updateTask(@RequestBody TaskDto taskDto, HttpServletRequest request) throws ParseException {
		return ResponseEntity.ok(taskService.updateTask(taskDto, request));
	}

	// to delete a specific task by taskId
	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable int id, HttpServletRequest request) {
		taskService.deleteTask(id, request);
	}

	// to get all available tasks with proper pagination
	@GetMapping("/pagination")
	public ResponseEntity<TaskPageResponse> getAllTasksWithPagination(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "2") int pageSize, HttpServletRequest request) {
		return ResponseEntity.ok(taskService.getAllTaskPages(pageNumber, pageSize,request));

	}

}
