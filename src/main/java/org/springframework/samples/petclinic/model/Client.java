package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client extends NamedEntity {

	private String dni;
	private String name;
	private String phone_number;

}
