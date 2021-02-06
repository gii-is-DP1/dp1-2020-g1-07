package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends NamedEntity {

	private String dni;
	private String name;
	private String phone_number;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

}
