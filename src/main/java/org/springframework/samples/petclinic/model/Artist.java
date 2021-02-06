package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="artists")
public class Artist extends Employee{
	
	@ManyToMany
	@JoinTable(
			  name = "acts", 
			  joinColumns = @JoinColumn(name = "artist_id"), 
			  inverseJoinColumns = @JoinColumn(name = "event_id"))
	private Collection<Event> acts;

}
