package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "slotgames")
public class Slotgame extends NamedEntity{
	
	private String name;
	
	private Integer jackpot;

}
