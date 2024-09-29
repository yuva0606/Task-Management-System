package com.yuva.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuva.app.entities.Task;
import com.yuva.app.service.TaskService;

@RestController
@RequestMapping("/admin/tasks")
public class AdminController {

	private final TaskService taskService;

	public AdminController(TaskService taskService) {
		super();
		this.taskService = taskService;
	}

	@GetMapping("")
	public ResponseEntity<List<Task>> getAllTasks() {

		return ResponseEntity.ok(taskService.adminGetAllTasks());

	}

	@DeleteMapping("/{taskId}")
	public void deleteTask(@PathVariable Integer taskId) {
		taskService.adminDeleteTask(taskId);
	}

}
