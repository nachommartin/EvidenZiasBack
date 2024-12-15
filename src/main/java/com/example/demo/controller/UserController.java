package com.example.demo.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.service.LoadFilesService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private LoadFilesService loader;
	
	
	@PostMapping
	public UserDTO saveUser(@Valid @RequestBody UserRequestDTO userDTO) {
		return userService.saveUser(userDTO);
	}
	
	@PutMapping(value= "/{userCode}")
	public UserDTO updateUser(@Valid @RequestBody UserRequestDTO userDTO, @PathVariable UUID userCode) throws Exception {
		return userService.updateUser(userDTO, userCode);
	}
	
	@GetMapping()
	public List<UserDTO> getAllUsers() throws Exception {
		return userService.getAllUsers();
	}

	
	@DeleteMapping(value= "/{userCode}")
	public void deleteUser(@PathVariable UUID userCode) throws Exception {
		 userService.deleteUser(userCode);
	}
	
	@GetMapping(value= "/{userCode}")
	public UserDTO getUser(@PathVariable UUID userCode) throws Exception {
		return userService.getUser(userCode);
	}
	
	@PutMapping("/{userCode}/image")
	public ResponseEntity<String> loadImages(@RequestBody MultipartFile file, @PathVariable UUID userCode) {
	    	try {
	        	byte[] imagen=this.loader.save(file);
	        	userService.loadImage(imagen, userCode);
	        		return new ResponseEntity<>(HttpStatus.OK);
	        	}catch(Exception ex) {
	        		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	        	}
		}  


}
