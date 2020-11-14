package org.springframework.samples.petclinic.web;


import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Menu;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MenuValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Menu menu = (Menu) obj;
		LocalDate date = menu.getDate();
		// Date validation
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
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
