package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID userCode =  UUID.randomUUID();
	private String nameUser;
	private String surnameUser;
	private String das;
	private Boolean isAdmin;
	private byte[] avatar;
	private List<TaskDTO> tasks;

}
