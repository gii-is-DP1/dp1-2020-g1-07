package org.springframework.samples.petclinic.web;

import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.Cook;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CookValidator implements Validator{
	private static final String REQUIRED = "required";

	@Override
	public void validate(Object target, Errors errors) {
		Cook cook = (Cook) target;
		String dni = cook.getDni();
		String name = cook.getName();
		String phone_number = cook.getPhone_number();
		//DNI validation
		if (dni == null || dni.trim().equals("") || !Pattern.matches("[0-9]{8}[a-z]{1}", dni)) {
			errors.rejectValue("dni", REQUIRED + "to contain 8 digits and a single lower-case letter",
					REQUIRED + "to contain 8 digits and a single lower-case letter");
		}
		//Name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		//Phone validation
		if (phone_number == null || phone_number.trim().equals("") || !Pattern.matches("[0-9]{9}", phone_number)) {
			errors.rejectValue("phone_number", REQUIRED + "to contain 9 digits",
					REQUIRED + "to contain 9 digits");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Cook.class.isAssignableFrom(clazz);
	}

	
}

