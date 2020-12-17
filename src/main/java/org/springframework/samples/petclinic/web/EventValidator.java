package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object object, Errors errors) {
		Event event = (Event)object;
		String name = event.getName();
		LocalDate date = event.getDate();
		//name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		// Date validation
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.isAssignableFrom(clazz);
	}
}
