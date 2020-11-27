package org.springframework.samples.petclinic.web;

import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.MaintenanceWorker;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MaintenanceWorkerValidator implements Validator{
	private static final String REQUIRED = "required";

	@Override
	public void validate(Object target, Errors errors) {
		MaintenanceWorker maintenanceWorker = (MaintenanceWorker) target;
		String dni = maintenanceWorker.getDni();
		String name = maintenanceWorker.getName();
		String phone_number = maintenanceWorker.getPhone_number();
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
		return MaintenanceWorker.class.isAssignableFrom(clazz);
	}

	
}

