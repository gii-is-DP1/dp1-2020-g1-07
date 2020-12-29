package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="schedules")

public class Schedule extends NamedEntity{

	
	@ManyToOne
	@JoinColumn(name = "employees_id")
	private Employee emp;
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
    private LocalDate date;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;
}
