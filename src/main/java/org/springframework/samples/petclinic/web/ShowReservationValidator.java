package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.service.ShowReservationService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ShowReservationValidator implements Validator{

	private static final String REQUIRED = "required";
	
	@Autowired
	private ShowReservationService showresService;
	
	@Override
	public void validate(Object obj, Errors errors) {
		ShowReservation showres = (ShowReservation) obj;
		Integer seats = showres.getSeats();
		Event event = showres.getEvent();
		Client client = showres.getClient();
		Collection<Event> bookable = showresService.findAvailableShows();
		// seats validation
		if (seats == null || seats < 1) {
			errors.rejectValue("seats", REQUIRED + "must be a number greater than zero",
					REQUIRED + "must be a number greater than zero");
		}
		// event validation
		if (event == null || bookable.contains(event) || showresService.findAvailableSeats(event.getId()) < seats) {
			errors.rejectValue("event", REQUIRED + "must be a future show with enough seats",
					REQUIRED + "must be a future show with enough seats");
		}
		// client validation
		if (client == null) {
			errors.rejectValue("client", REQUIRED, REQUIRED);
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ShowReservation.class.isAssignableFrom(clazz);
	}
}
