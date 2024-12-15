package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.demo.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks", schema= "evidenzias")  
public class Task {
	

	@Id
	@GeneratedValue 
	@Column(name="task_code")
	private UUID taskCode =  UUID.randomUUID();
	@Column(name="name_task")
	private String nameTask;
	private String description;
	private String observations;
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	@Column(name="create_date")
	private LocalDateTime createDate;
	@Column(name="modify_date")
	private LocalDateTime modifyDate;
	
	@ManyToOne
	@JoinColumn(name = "supervisor_id", nullable = false)
	private User supervisor;

	@OneToMany(mappedBy = "task")
	List<AssignedTask> assignations = new ArrayList<>();

	

}
