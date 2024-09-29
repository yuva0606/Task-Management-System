package com.yuva.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuva.app.entities.Task;

public interface TaskRepo extends JpaRepository<Task, Integer> {

	List<Task> findAllByUsername(String username);

}
