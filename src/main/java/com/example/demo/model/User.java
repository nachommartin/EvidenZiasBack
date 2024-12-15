package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Data
@NoArgsConstructor
@Entity
@Table(name = "users", schema= "evidenzias")  
public class User {
	
	@Id
	@GeneratedValue 
	@Column(name="user_code")
	private UUID userCode =  UUID.randomUUID();
	@Column(name="name_user")
	private String nameUser;
	@Column(name="surname_user")
	private String surnameUser;
	private String das;
	@Column(name="ind_admin")
	private Boolean isAdmin;
	@Column(name="avatar", columnDefinition="bytea")
	private byte[] avatar;
	
	@OneToMany(mappedBy = "supervisor")
	private List<Task> supervisedTasks = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	List<AssignedTask> assignations = new ArrayList<>();


}
