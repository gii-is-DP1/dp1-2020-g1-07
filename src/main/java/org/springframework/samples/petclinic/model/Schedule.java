package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="schedules")

public class Schedule {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee emp;
	
	
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;
}
