package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Dish extends NamedEntity{

	private String name;
	private DishCourse dishCourse;
	private Shift shift;
	
}