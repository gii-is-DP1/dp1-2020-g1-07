package org.springframework.samples.petclinic.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="employees")

public class Employee extends NamedEntity{

	@NotEmpty
	@Pattern(regexp="^[0-9]{8}[A-Z]$",message="DNI must contain 8 digits and a single capital letter")  
	private String dni;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Pattern(regexp="^[0-9]{9}$",message="phone number must contain 9 digits")  
	@Column(name = "phone_number")
	private String phone_number;
	
}


