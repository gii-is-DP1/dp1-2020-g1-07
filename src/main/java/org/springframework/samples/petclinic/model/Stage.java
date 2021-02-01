package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stage extends NamedEntity{
	
	
	private Integer capacity;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event_id;
	
	
}
