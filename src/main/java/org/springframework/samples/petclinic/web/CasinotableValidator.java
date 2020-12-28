package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CasinotableValidator implements Validator {

	private static final String REQUIRED = "required";

	protected CasinotableService casinotableService;
	
	@Override
	public void validate(Object obj, Errors errors) {
		Casinotable casinotable = (Casinotable) obj;
		String name = casinotable.getGame().getName();
		
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Dish.class.isAssignableFrom(clazz);
	}
	
}

