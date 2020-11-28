package org.springframework.samples.petclinic.web;

import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.Client;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ClientValidator implements Validator {
	
	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Client client = (Client) obj;
		String name = client.getName();
		String dni = client.getDni();
		String phoneNumber = client.getPhone_number();
		// name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}

		// dni validation
		if (dni == null || dni.trim().equals("") || !Pattern.matches("[0-9]{8}[A-Z]{1}", dni)) {
			errors.rejectValue("dni", REQUIRED + " to have 8 numbers and one capital letter at the end", REQUIRED + " to have 8 numbers and one capital letter at the end");
		}

		// phoneNumber date validation
		if (phoneNumber == null || phoneNumber.trim().equals("") || !Pattern.matches("[0-9]{9}", phoneNumber)) {
			errors.rejectValue("phone_number", REQUIRED + " to have 9 numbers", REQUIRED + " to have 9 numbers");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

}
