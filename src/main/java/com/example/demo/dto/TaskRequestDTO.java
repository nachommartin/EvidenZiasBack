package com.example.demo.dto;

import java.io.Serializable;
import java.util.UUID;


import lombok.Data;

@Data
public class TaskRequestDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nameTask;
	private String description;
	private String observations;
	private UUID supervisorCode;
	private String modifiedStatus;

}
