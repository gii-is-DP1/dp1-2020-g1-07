package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="croupiers")
public class Croupier extends Employee{
	
	@ManyToOne
	@JoinColumn(name = "casinotable")
	private Casinotable casinotable;
	
}
