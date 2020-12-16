package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class StageValidator implements Validator{

	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object object, Errors errors) {
		Stage stage = (Stage) object;
		Integer capacity = stage.getCapacity();
		//capacity validation
		if(capacity == null || capacity == 0) {
			errors.rejectValue("capacity", REQUIRED,REQUIRED);
		}
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Game.class.isAssignableFrom(clazz);
	}

	
}
