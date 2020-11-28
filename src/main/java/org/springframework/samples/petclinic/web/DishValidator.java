package org.springframework.samples.petclinic.web;

<<<<<<< Updated upstream
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.service.DishService;
=======


import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Person;
>>>>>>> Stashed changes
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DishValidator implements Validator {

	private static final String REQUIRED = "required";
<<<<<<< Updated upstream
	protected DishService dishService;
	private Integer prueba = 0;
=======
	 
	
>>>>>>> Stashed changes

	public Dish getDishwithIdDifferent(String name, Integer id) {
		name = name.toLowerCase();
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Dish dish : dishes) {
			String compName = dish.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && dish.getId()!=id) {
				return dish;
			}
		}
		return null;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Dish dish = (Dish) obj;
		String name = dish.getName();
		//Dish otherDish=getDishwithIdDifferent(dish.getName(), dish.getId());
		// name validation
		if (name == null || name.trim().equals("") /*|| (otherDish!= null && otherDish.getId()!=dish.getId())*/) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
			prueba=1;
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Dish.class.isAssignableFrom(clazz);
	}
	
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
	

}
