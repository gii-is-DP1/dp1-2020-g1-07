package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User{
	
	@Id
	String username;
	
	String password;
	
	@JoinColumn(name = "dni")
	String dni;
	
	boolean enabled;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	//private Set<Authorities> authorities;
}
