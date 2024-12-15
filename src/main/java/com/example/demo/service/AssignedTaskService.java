package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AssignedTask;
import com.example.demo.model.AssignedTaskKey;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.AssignedTaskRepository;

@Service
public class AssignedTaskService {

	@Autowired AssignedTaskRepository repository;
	
	public List<AssignedTask> getByUser(User user){
		return repository.findByUser(user);
	}
	

	public List<AssignedTask> getByTask(Task task){
		return repository.findByTaskOrderByAssignedDateAsc(task);
	}
	
	public void assignTask(User user, Task task) {
		AssignedTask asTask = new AssignedTask();
		AssignedTaskKey id = new AssignedTaskKey();
	    id.setTask(task.getTaskCode()); 
	    id.setUser(user.getUserCode());  
	    asTask.setId(id);  
		asTask.setTask(task);
		asTask.setUser(user);
		asTask.setAssignedDate(LocalDateTime.now());
		repository.save(asTask);
	}
	
	public void unAssignTask(UUID userCode, UUID taskCode) throws Exception {
		Optional<AssignedTask> asTask= repository.findById(new AssignedTaskKey(taskCode, userCode));
		if(asTask.isEmpty()) {
			throw new Exception("No existe esa asignación entre usuario y tarea");
		}
		repository.delete(asTask.get());
	}
	
	
}
