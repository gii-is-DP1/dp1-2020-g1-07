package org.springframework.samples.petclinic.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="employees")

public class Employee extends NamedEntity{

	@NotEmpty
	@Pattern(regexp="^[0-9]{8}[a-z]$",message="DNI must contain 8 digits and a single lower-case letter")  
	private String dni;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Pattern(regexp="^[0-9]{9}$",message="phone number must contain 9 digits")  
	@Column(name = "phone_number")
	private String phone_number;
	
}


