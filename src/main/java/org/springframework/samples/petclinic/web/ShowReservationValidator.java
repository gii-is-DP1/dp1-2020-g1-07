package org.springframework.samples.petclinic.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.service.ShowReservationService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ShowReservationValidator implements Validator{

	private static final String REQUIRED = "required";
	
	@Autowired
	private ShowReservationService showresService;
	
	@Override
	public void validate(Object obj, Errors errors) {
		ShowReservation showres = (ShowReservation) obj;
		Integer seats = showres.getSeats();
		Event event = showres.getEvent();
		Collection<Event> bookable = showresService.findAvailableShows();
		// event validation
		if (event == null || !bookable.contains(event))  {
			errors.rejectValue("event", REQUIRED + " to be a future show with enough seats",
					REQUIRED + " to be a future show with enough seats");
		}
		// seats validation
		if (seats == null || seats < 1) {
			errors.rejectValue("seats", REQUIRED + " to be a number greater than zero",
					REQUIRED + " to be a number greater than zero");
		}
		else if(!errors.hasFieldErrors("event")){
			Integer max = showresService.findAvailableSeats(event.getId());
			if (max < seats) {
				errors.rejectValue("seats", REQUIRED + " to not be greater than the available seats: " + max,
				REQUIRED + " to not be greater than the available seats: " + max);
			}
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ShowReservation.class.isAssignableFrom(clazz);
	}
}
