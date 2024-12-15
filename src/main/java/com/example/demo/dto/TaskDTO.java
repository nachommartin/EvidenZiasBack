package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.demo.enums.TaskStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class TaskDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID taskCode =  UUID.randomUUID();
	private String nameTask;
	private String description;
	private String observations;
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	private LocalDateTime oldAssignation;
	private UserDTO supervisor;
	private List<UserDTO> assignations; 

}
