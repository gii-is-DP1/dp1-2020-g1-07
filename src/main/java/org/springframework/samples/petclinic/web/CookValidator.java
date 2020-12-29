package org.springframework.samples.petclinic.web;

import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.service.CookService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CookValidator implements Validator{
	private static final String REQUIRED = "required";

	@Autowired
	private CookService cookService;
	
	public boolean getCookwithIdDifferent(String dni, Integer id) {
		if (StreamSupport.stream(this.cookService.findAll().spliterator(), false)
				.filter(x -> (x.getDni().equals(dni) && id == null) || 
						(x.getDni().equals(dni) && id != null && !x.getId().equals(id)))
				.findAny().isPresent())
			return true;
		else
			return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Cook cook = (Cook) target;
		String dni = cook.getDni();
		String name = cook.getName();
		String phone_number = cook.getPhone_number();
		
		//DNI validation
		if (dni == null || dni.trim().equals("") || !Pattern.matches("[0-9]{8}[A-Z]{1}", dni)) {
			errors.rejectValue("dni", REQUIRED + " to contain 8 digits and a single capital letter",
					REQUIRED + " to contain 8 digits and a single capital letter");
		}
		
		//Name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		
		//Phone validation
		if (phone_number == null || phone_number.trim().equals("") || !Pattern.matches("[0-9]{9}", phone_number)) {
			errors.rejectValue("phone_number", REQUIRED + " to contain 9 digits",
					REQUIRED + " to contain 9 digits");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Cook.class.isAssignableFrom(clazz);
	}

	
}

