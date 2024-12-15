package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AssignedTask;
import com.example.demo.model.AssignedTaskKey;
import com.example.demo.model.Task;
import com.example.demo.model.User;

public interface AssignedTaskRepository extends JpaRepository<AssignedTask, AssignedTaskKey>{
	
	List<AssignedTask> findByUser(User user);
	
	List<AssignedTask> findByTaskOrderByAssignedDateAsc(Task task);


}
