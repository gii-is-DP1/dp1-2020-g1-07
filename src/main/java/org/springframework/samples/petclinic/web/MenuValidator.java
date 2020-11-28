package org.springframework.samples.petclinic.web;


import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MenuValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Menu menu = (Menu) obj;
		LocalDate date = menu.getDate();
		Shift shift = menu.getShift();
		Dish first_dish = menu.getFirst_dish();
		Dish second_dish = menu.getSecond_dish();
		Dish dessert = menu.getDessert();
		// Date validation
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		// Dishes validation
		if(first_dish==null || shift.getId() != first_dish.getShift().getId()) {
			errors.rejectValue("first_dish", REQUIRED + " that the dish is not in the selected shift", REQUIRED + " that the dish is not in the selected shift");
		}
		if(shift.getId() != second_dish.getShift().getId()) {
			errors.rejectValue("second_dish", REQUIRED + " that the dish is not in the selected shift", REQUIRED + " that the dish is not in the selected shift");
		}
		if(shift.getId() != dessert.getShift().getId()) {
			errors.rejectValue("dessert", REQUIRED + " that the dish is not in the selected shift", REQUIRED + " that the dish is not in the selected shift");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Menu.class.isAssignableFrom(clazz);
	}

}
