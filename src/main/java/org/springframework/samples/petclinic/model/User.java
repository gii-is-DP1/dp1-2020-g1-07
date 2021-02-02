package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	
	@Id
	String username;
	
	String password;
	
	String dni; //TEMPORAL
	
	boolean enabled;
}
