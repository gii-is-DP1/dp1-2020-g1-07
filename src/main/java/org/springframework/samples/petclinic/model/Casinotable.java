package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Casinotable extends NamedEntity{

	private String name;
	private String game;
	
}
