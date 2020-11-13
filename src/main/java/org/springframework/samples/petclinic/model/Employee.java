package org.springframework.samples.petclinic.model;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="employee")

public class Employee extends NamedEntity{

	private String dni;
	private String name;
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;
	
}


