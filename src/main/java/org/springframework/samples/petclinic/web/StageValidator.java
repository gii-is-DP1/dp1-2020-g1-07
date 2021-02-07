package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StageValidator implements Validator{

	private static final String REQUIRED = "required";
	
	@Autowired
	private EventService eventservice;
	
	@Override
	public void validate(Object object, Errors errors) {
		Stage stage = (Stage) object;
	
		Integer capacity = stage.getCapacity();
		//capacity validation
		if(capacity == null || capacity < 1) {
			errors.rejectValue("capacity", REQUIRED,REQUIRED);
		}

	}
	public boolean isUsedInEvent(Stage stage) {
		boolean result = false;
		List<Event> events = StreamSupport.stream(this.eventservice.findAll().spliterator(), false).collect(Collectors.toList());
		for(Event event: events) {
			Integer eventstage = event.getStage_id().getId();
			Integer stageid = stage.getId();
			
			if(eventstage == stageid) result = true;
		}
		return result;
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Stage.class.isAssignableFrom(clazz);
	}

	
}
