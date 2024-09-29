package com.yuva.app.controller;

import java.text.ParseException;
import java.util.List;

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
	@PostMapping("/create")
	public Task createTask(@RequestBody TaskDto taskDto, HttpServletRequest request) throws ParseException {
		return taskService.createTask(taskDto, request);
	}

	// to fetch a specific task by taskId
	@GetMapping("/{id}")
	public TaskDto getTaskById(@PathVariable int id, HttpServletRequest request) {
		return taskService.getTaskById(id, request);
	}

	// to fetch all tasks
	@GetMapping("/")
	public List<TaskDto> getAllTasks(HttpServletRequest request) {
		System.out.println("Trying to get all tasks");
		return taskService.getAllTasks(request);
	}

	// to update a specific task
	@PutMapping("/update")
	public Task updateTask(@RequestBody TaskDto taskDto, HttpServletRequest request) throws ParseException {
		return taskService.updateTask(taskDto, request);
	}

	// to delete a specific task by taskId
	@DeleteMapping("/delete/{id}")
	public void deleteTask(@PathVariable int id, HttpServletRequest request) {
		taskService.deleteTask(id, request);
	}

	// to get all available tasks with proper pagination
	@GetMapping("/pagination")
	public TaskPageResponse getAllTasksWithPagination(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "2") int pageSize) {
		return taskService.getAllTaskPages(pageNumber, pageSize);

	}

}
