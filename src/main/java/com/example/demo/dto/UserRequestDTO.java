package com.example.demo.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @NotNull
	private String nameUser;
    @NotNull
	private String surnameUser;
    @NotNull
	private String das;
	private Boolean isAdmin;
	
	

}
