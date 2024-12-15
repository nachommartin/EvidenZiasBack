package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AssignationDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.TaskRequestDTO;
import com.example.demo.service.TaskService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired TaskService taskService;
	
	@PostMapping
	public TaskDTO saveTask(@Valid @RequestBody TaskRequestDTO taskDTO) throws Exception {
		return taskService.saveTask(taskDTO);
	}
	
	@PutMapping(value="/{taskCode}")
	public TaskDTO modifyTask(@Valid @RequestBody TaskRequestDTO taskDTO, @PathVariable UUID taskCode) throws Exception {
		return taskService.updateTask(taskDTO, taskCode);
	}
	
	@GetMapping
	public List<TaskDTO> getAllTask(){
		return taskService.getAllTask();
	}
	
	@PostMapping(value="/assign")
	public void assignTask(@Valid @RequestBody AssignationDTO assignationDTO) throws Exception {
		taskService.assignTask(assignationDTO);
	}
	
	@PostMapping(value="/unassign")
	public void unAssignTask(@Valid @RequestBody AssignationDTO assignationDTO) throws Exception {
		taskService.unAssignTask(assignationDTO);
	}

}
