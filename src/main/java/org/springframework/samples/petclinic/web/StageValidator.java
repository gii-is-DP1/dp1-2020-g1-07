package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StageValidator implements Validator{

	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object object, Errors errors) {
		Stage stage = (Stage) object;
		Event event = stage.getEvent_id();
		Integer capacity = stage.getCapacity();
		//capacity validation
		if(capacity == null || capacity == 0) {
			errors.rejectValue("capacity", REQUIRED,REQUIRED);
		}
		//event validation
		if(event == null || event.getName().equals("")) {
			errors.rejectValue("event_id", REQUIRED,REQUIRED);
		}
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Stage.class.isAssignableFrom(clazz);
	}

	
}
