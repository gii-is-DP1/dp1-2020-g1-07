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
@Table(name="cooks")
public class Cook extends Employee{
	
	@ManyToMany
	@JoinTable(
			  name = "prepares", 
			  joinColumns = @JoinColumn(name = "cook_id"), 
			  inverseJoinColumns = @JoinColumn(name = "dish_id"))
	private Collection<Dish> prepares;

}
