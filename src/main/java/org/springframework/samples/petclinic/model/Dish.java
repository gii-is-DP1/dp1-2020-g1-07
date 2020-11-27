package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.service.DishService;

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