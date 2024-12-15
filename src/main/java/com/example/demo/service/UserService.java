package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired UserRepository repository;
	@Autowired TaskService taskService;
	
	public UserDTO getUser (UUID userCode) throws Exception {
		UserDTO returned = new UserDTO();
		Optional<User> opt = repository.findById(userCode);
		if(opt.isPresent()) {
			BeanUtils.copyProperties(opt.get(),returned);
			List<TaskDTO> tasks = taskService.getTasksByUsers(opt.get());
			returned.setTasks(tasks);
		}
		else{
			throw new Exception ("No existe ese usuario");
		}
		return returned;
	}
	
	public List<UserDTO> getAllUsers() throws Exception {
		List<UserDTO> returnedList = new ArrayList<>();
		List<User> users = repository.findAll();
		for(User user:users) {
			UserDTO userReturned = new UserDTO();
			BeanUtils.copyProperties(user,userReturned);
			returnedList.add(userReturned);
		}
		return returnedList;
	}

	public UserDTO saveUser(UserRequestDTO userDTO) {
		User user = new User();
		UserDTO returned = new UserDTO();
		BeanUtils.copyProperties(userDTO,user);
		repository.save(user);
		BeanUtils.copyProperties(user,returned);
		return returned;		
	}
	
	public UserDTO updateUser(UserRequestDTO userDTO, UUID userCode) throws Exception{
		UserDTO returned = new UserDTO();
		Optional<User> opt = repository.findById(userCode);
		if(opt.isPresent()) {
			BeanUtils.copyProperties(userDTO,opt.get());
			repository.save(opt.get());
			BeanUtils.copyProperties(opt.get(),returned);
		}
		else{
			throw new Exception ("No existe ese usuario");
		}
		return returned;		
	}
	
	public void deleteUser (UUID userCode) throws Exception {
		Optional<User> opt = repository.findById(userCode);
		if(opt.isPresent()) {
			repository.delete(opt.get());
		}
		else{
			throw new Exception ("No existe ese usuario");
		}
	}
	
	public void loadImage (byte[] imagen, UUID userCode) throws Exception{
		Optional<User> opt = repository.findById(userCode);

		if (opt.isEmpty()) {
			throw new Exception ("No existe ese usuario");
		} 
    		opt.get().setAvatar(imagen);
			repository.save(opt.get());
	}
}
