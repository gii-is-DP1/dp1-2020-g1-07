package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.StageService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Autowired
	private EventService eventservice;
	
	@Autowired
	private StageService stageservice;
	
	public Boolean eventWithTheSameName(String name) {
		name = name.toLowerCase();
		Boolean result = false;
		List<Event> events = StreamSupport.stream(this.eventservice.findAll().spliterator(), false).collect(Collectors.toList());
		for (Event event : events) {
			String nameevent = event.getName();
			nameevent = nameevent.toLowerCase();
			if (nameevent.equals(name)) {
				result = true;
			}
		}
		return result;
	}
	
	public Boolean eventWithTheSameName_Update(String name, Integer id) {
		name = name.toLowerCase();
		Boolean result = false;
		List<Event> events = StreamSupport.stream(this.eventservice.findAll().spliterator(), false).collect(Collectors.toList());
		for (Event event : events) {
			String nameevent = event.getName();
			nameevent = nameevent.toLowerCase();
			if (nameevent.equals(name) && event.getId()!=id) {
				result = true;
			}
		}
		return result;
	}
	
	
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