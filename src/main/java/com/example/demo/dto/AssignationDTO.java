package com.example.demo.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class AssignationDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID userCode;
	private UUID taskCode;
	

}
