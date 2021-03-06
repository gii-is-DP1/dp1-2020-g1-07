package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dishes", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Dish extends NamedEntity{

	@NotBlank
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "dish_course_id")
	private DishCourse dish_course;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;
	
	@ManyToMany(mappedBy = "prepares")
	private Set<Cook> cooks;
	
}