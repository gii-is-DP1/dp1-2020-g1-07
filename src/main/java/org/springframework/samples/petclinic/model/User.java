package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	
	@Id
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotNull
	private boolean enabled;
	
	@NotBlank
	private String dni; //TEMPORAL
	
}
