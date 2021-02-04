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
@Table(name="waiters")
public class Waiter extends Employee{
	
	@ManyToMany
	@JoinTable(
			  name = "serves", 
			  joinColumns = @JoinColumn(name = "waiter_id"), 
			  inverseJoinColumns = @JoinColumn(name = "table_id"))
	private Collection<RestaurantTable> serves;
	
}
