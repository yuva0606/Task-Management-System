package com.yuva.app.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yuva.app.dto.TaskDto;
import com.yuva.app.entities.Role;
import com.yuva.app.entities.Task;
import com.yuva.app.exceptions.TaskNotFoundException;
import com.yuva.app.exceptions.UnAuthorizedTaskAccessExecption;
import com.yuva.app.repository.TaskRepo;
import com.yuva.app.security.service.JwtService;
import com.yuva.app.security.service.UserService;
import com.yuva.app.utils.TaskPageResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

	private final TaskRepo taskRepo;

	private final JwtService jwtService;

	private final UserService userService;

	public TaskService(TaskRepo taskRepo, JwtService jwtService, UserService userService) {
		super();
		this.taskRepo = taskRepo;
		this.jwtService = jwtService;
		this.userService = userService;
	}

	public TaskDto getTaskById(int taskId, HttpServletRequest request) {
		Task task = taskRepo.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException("Task not found with id " + taskId));
		//getting username from jwt to verify the authorization access of the user
		String username = getUsername(request);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		//checking if the user is the owner of the task
		if (!username.equals(task.getusername()))
			throw new UnAuthorizedTaskAccessExecption("Can not access the task with id " + taskId);

		TaskDto taskDto = new TaskDto(task.getTaskId(), task.getTitle(), task.getDescription(), df.format(task.getDueDate()),
				task.getPriority());
		return taskDto;
	}

	public List<TaskDto> getAllTasks(HttpServletRequest request) {
		//getting username from jwt to fetch all the tasks belongs to the user
		String username = getUsername(request);
		List<Task> tasks = taskRepo.findAllByUsername(username);
		List<TaskDto> taskDtos = new ArrayList<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Task task : tasks) {
			TaskDto taskDto = new TaskDto(task.getTaskId(), task.getTitle(), task.getDescription(), df.format(task.getDueDate()),
					task.getPriority());
			taskDtos.add(taskDto);
		}
		return taskDtos;
	}

	public Task createTask(TaskDto taskDto, HttpServletRequest request) throws ParseException {
		//getting username from the jwt
		String username = getUsername(request);
		Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(taskDto.getDueDate());
		Task newTask = new Task(null, taskDto.getTitle(), taskDto.getDescription(), dueDate,
				taskDto.getPriority(), username);
		return taskRepo.save(newTask);
	}

	public Task updateTask(TaskDto taskDto, HttpServletRequest request) throws ParseException {

		Task task = taskRepo.findById(taskDto.getTaskId())
				.orElseThrow(() -> new TaskNotFoundException("Task not found with id " + taskDto.getTaskId()));

		//getting username from jwt to verify the authorization access of the user
		String username = getUsername(request);

		//checking if the user is the owner of the task
		if (!username.equals(task.getusername()))
			throw new UnAuthorizedTaskAccessExecption("Can not access the task with id " + taskDto.getTaskId());
		Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(taskDto.getDueDate());

		Task newTask = new Task(taskDto.getTaskId(), taskDto.getTitle(), taskDto.getDescription(), dueDate,
				taskDto.getPriority(), task.getusername());
		return taskRepo.save(newTask);
	}

	public void deleteTask(int id, HttpServletRequest request) {
		Task task = taskRepo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));

		//getting username from jwt to verify the authorization access of the user
		String username = getUsername(request);

		//checking if the user is the owner of the task
		if (!username.equals(task.getusername()))
			throw new UnAuthorizedTaskAccessExecption("Can not access the task with id " + id);

		taskRepo.delete(task);
	}

	public TaskPageResponse getAllTaskPages(int pageNumber, int pageSize, HttpServletRequest request) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		String username = getUsername(request);
		Page<Task> taskpages = taskRepo.findAll(pageable);
		List<Task> tasks = taskpages.getContent();
		List<TaskDto> taskDtos = new ArrayList<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Task task : tasks) {
			if(!username.equals(task.getusername()))
				continue;
			TaskDto taskDto = new TaskDto(task.getTaskId(), task.getTitle(), task.getDescription(), df.format(task.getDueDate()),
					task.getPriority());
			taskDtos.add(taskDto);
		}

		return new TaskPageResponse(taskDtos, pageNumber, pageSize, taskpages.getTotalElements(),
				taskpages.getTotalPages(), taskpages.isLast());

	}
	
	// helper method to extract username from the Jwt token
	private String getUsername(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;

		// getting the JWT token from authHeader
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			// extracting the username from the JWT token
			userName = jwtService.extractUserName(token);
		}

		return userName;
	}

	public List<Task> adminGetAllTasks() {
		return taskRepo.findAll();
	}

	public void adminDeleteTask(Integer taskId) {
		taskRepo.deleteById(taskId);
		
	}

}
