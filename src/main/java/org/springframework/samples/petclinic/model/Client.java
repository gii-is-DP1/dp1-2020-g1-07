package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends NamedEntity {

	@NotBlank
	@Pattern(regexp="^[0-9]{8}[A-Z]$",message="DNI must contain 8 digits and a single capital letter")  
	private String dni;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Pattern(regexp="^[0-9]{9}$",message="phone number must contain 9 digits")  
	@Column(name = "phone_number")
	private String phone_number;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

}
