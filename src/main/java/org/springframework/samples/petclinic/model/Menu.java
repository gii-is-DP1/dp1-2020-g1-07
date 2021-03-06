package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Menu extends NamedEntity {
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
	private LocalDate date;
	
	@ManyToOne
    @JoinColumn(name = "first_dish_id")
	private Dish first_dish;
	
	@ManyToOne
    @JoinColumn(name = "second_dish_id")
	private Dish second_dish;
	
	@ManyToOne
    @JoinColumn(name = "dessert_id")
	private Dish dessert;
	
	@ManyToOne
    @JoinColumn(name = "shift_id")
	private Shift shift;
		
}
