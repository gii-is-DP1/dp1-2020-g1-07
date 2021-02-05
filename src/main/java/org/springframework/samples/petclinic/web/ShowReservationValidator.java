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
			errors.rejectValue("event", REQUIRED + "must be a future show with enough seats",
					REQUIRED + "must be a future show with enough seats");
		}
		// seats validation
		else if (seats == null || seats < 1) {
			errors.rejectValue("seats", REQUIRED + "must be a number greater than zero",
					REQUIRED + "must be a number greater than zero");
		}
		else {
			Integer max = showresService.findAvailableSeats(event.getId());
			if (max < seats) {
				errors.rejectValue("seats", REQUIRED + "can't be greater than the available seats: " + max,
				REQUIRED + "can't be greater than the available seats: " + max);
			}
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ShowReservation.class.isAssignableFrom(clazz);
	}
}
