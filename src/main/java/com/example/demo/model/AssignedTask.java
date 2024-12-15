package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "task_assigned_users", schema="evidenzias")
public class AssignedTask {
	
	 @EmbeddedId
	 private AssignedTaskKey id;

	 @ManyToOne
	 @MapsId("task")
	 @JoinColumn(name = "task_id")
	 private Task task;

	 @ManyToOne
	 @MapsId("user")
	 @JoinColumn(name = "user_id")
	 private User user;

	 @Column(name ="assigned_date")
	 private LocalDateTime assignedDate;
	 
	 
	    

}
