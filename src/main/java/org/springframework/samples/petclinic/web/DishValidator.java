package org.springframework.samples.petclinic.web;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DishValidator implements Validator {

	private static final String REQUIRED = "required";

	@Autowired
	private DishService dishService;
	
	private Integer prueba = 0;
	
	public Dish getDishwithIdDifferent(String name) {
		name = name.toLowerCase();
		Dish empty_dish = new Dish();
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Dish dish : dishes) {
			String compName = dish.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name)) {
				return dish;
			}
		}
		return empty_dish;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Dish dish = (Dish) obj;
		String name = dish.getName();
		Dish otherDish=getDishwithIdDifferent(dish.getName());
		// name validation
		if (name == null || name.trim().equals("") /*|| (otherDish!= null && otherDish.getId()!=dish.getId())*/) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
			prueba=1;
		}
		if(otherDish.getName()!=null) {
			errors.rejectValue("name", "El nombre no puede estar repetido", "El nombre no puede estar repetido");
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
	

	

}
