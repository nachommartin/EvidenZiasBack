package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AssignationDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.TaskRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.enums.TaskStatus;
import com.example.demo.model.AssignedTask;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Service
public class TaskService {
	
	@Autowired TaskRepository repository;
	@Autowired UserRepository userRepository;
	@Autowired AssignedTaskService asTaskService;
	
	public List<TaskDTO> getTasksByUsers(User user){
		List<TaskDTO> returned = new ArrayList<>();
		List<AssignedTask> assignedTasks = asTaskService.getByUser(user);
		for(AssignedTask asTask: assignedTasks) {
			TaskDTO task = new TaskDTO();
			BeanUtils.copyProperties(asTask.getTask(),task);
			task.setOldAssignation(asTask.getAssignedDate());
			UserDTO supervisor = new UserDTO();
			BeanUtils.copyProperties(asTask.getTask().getSupervisor(),supervisor);
			task.setSupervisor(supervisor);
			returned.add(task);
		}
		return returned;
		
	}
	

	public TaskDTO saveTask(TaskRequestDTO taskDTO) throws Exception {
		Task task = new Task();
		TaskDTO returned = new TaskDTO();
		UserDTO userReturned= new UserDTO();
		BeanUtils.copyProperties(taskDTO,task);
		Optional<User> supervisor = userRepository.findById(taskDTO.getSupervisorCode());
		if(supervisor.isEmpty()) {
			throw new Exception ("No puede ser supervisor un usuario que no existe");
		}
		BeanUtils.copyProperties(supervisor.get(),userReturned);
		task.setSupervisor(supervisor.get());
		task.setStatus(TaskStatus.PENDING);
		task.setCreateDate(LocalDateTime.now());
		repository.save(task);
		BeanUtils.copyProperties(task,returned);
		returned.setSupervisor(userReturned);
		return returned;		
	}
	
	public TaskDTO updateTask(TaskRequestDTO taskDTO, UUID taskCode) throws Exception {
		TaskDTO returned = new TaskDTO();
		UserDTO userReturned= new UserDTO();
		Optional<Task> opt = repository.findById(taskCode);
		if(opt.isPresent()) {
			BeanUtils.copyProperties(taskDTO,opt.get());
			Optional<User> supervisor = userRepository.findById(taskDTO.getSupervisorCode());
			if(supervisor.isEmpty()) {
				throw new Exception ("No puede ser supervisor un usuario que no existe");
			}
			opt.get().setSupervisor(supervisor.get());
			opt.get().setStatus(TaskStatus.valueOf(taskDTO.getModifiedStatus()));
			opt.get().setModifyDate(LocalDateTime.now());
			repository.save(opt.get());
			BeanUtils.copyProperties(opt.get(),returned);
			BeanUtils.copyProperties(supervisor.get(),userReturned);
			returned.setSupervisor(userReturned);
			List<AssignedTask> assignations= asTaskService.getByTask(opt.get());
			List<UserDTO> usersAssigned = new ArrayList<>();
			for(AssignedTask as:assignations) {
				UserDTO userAs = new UserDTO();
				BeanUtils.copyProperties(as.getUser(),userAs);
				usersAssigned.add(userAs);
			}
			returned.setAssignations(usersAssigned);
		}
		else{
			throw new Exception ("No existe esa tarea");
		}
		
		return returned;		
	}
	
	public List<TaskDTO> getAllTask() {
		List<TaskDTO> returnedList = new ArrayList<>();
		List<Task> tasks = repository.findAll();
		for(Task task:tasks) {
			TaskDTO taskReturned = new TaskDTO();
			BeanUtils.copyProperties(task, taskReturned);
			taskReturned.setOldAssignation(findByTaskOrderByAssignedDateAsc(task));
			UserDTO supervisor = new UserDTO();
			BeanUtils.copyProperties(task.getSupervisor(),supervisor);
			taskReturned.setSupervisor(supervisor);
			List<AssignedTask> assignations= asTaskService.getByTask(task);
			List<UserDTO> usersAssigned = new ArrayList<>();
			for(AssignedTask as:assignations) {
				UserDTO userAs = new UserDTO();
				BeanUtils.copyProperties(as.getUser(),userAs);
				usersAssigned.add(userAs);
			}
			taskReturned.setAssignations(usersAssigned);
			returnedList.add(taskReturned);
		}
		return returnedList;
	}

	public void assignTask(AssignationDTO assignationDTO) throws Exception {
		Optional<Task> optTask = repository.findById(assignationDTO.getTaskCode());
		if(optTask.isEmpty()) {
			throw new Exception ("No existe esa tarea");
		}
		Optional<User> optUser = userRepository.findById(assignationDTO.getUserCode());
		if(optUser.isEmpty()) {
			throw new Exception ("No existe esa tarea");
		}
		if(optUser.get().equals(optTask.get().getSupervisor())) {
			throw new Exception("Un usuario no se puede asignar la tarea que supervisa");
		}
		asTaskService.assignTask(optUser.get(), optTask.get());
		
	}
	
	public void unAssignTask(AssignationDTO assignationDTO) throws Exception {
		
		asTaskService.unAssignTask(assignationDTO.getUserCode(), assignationDTO.getTaskCode());
		
	}
	
	private LocalDateTime findByTaskOrderByAssignedDateAsc(Task task) {
		if(!asTaskService.getByTask(task).isEmpty()) {
			return asTaskService.getByTask(task).get(0).getAssignedDate();
		}
		else {
			return null;
		}
	}

}
