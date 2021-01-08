package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dishes", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Dish extends NamedEntity{

	private String name;

	@ManyToOne
	@JoinColumn(name = "dish_course_id")
	private DishCourse dish_course;
	
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;
	
}