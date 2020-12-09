package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dishes")
public class Dish extends NamedEntity{

	private String name;

	@ManyToOne
	@JoinColumn(name = "dish_course_id")
	private DishCourse dish_course;
	
	
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;

	
	
	
}