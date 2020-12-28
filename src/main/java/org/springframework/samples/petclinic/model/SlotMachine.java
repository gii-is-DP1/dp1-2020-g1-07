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
@Table(name = "slotmachines")
public class SlotMachine extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "slotgame_id")
	private Slotgame slotgame;
	
}
