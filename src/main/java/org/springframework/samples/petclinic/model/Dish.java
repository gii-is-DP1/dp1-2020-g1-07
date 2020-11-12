package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Dish extends NamedEntity{

	private String name;

	@ManyToOne
	@JoinColumn(name = "dish_course_id")
	private DishCourse dish_course;
	
	
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;
	
}