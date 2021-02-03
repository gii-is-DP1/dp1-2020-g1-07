package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RestaurantReservationValidator implements Validator{
	
	@Override
	public void validate(Object obj, Errors errors) {
		RestaurantReservation reservation = (RestaurantReservation) obj;
		Client client = reservation.getClient();
		LocalDate date = reservation.getDate();
		TimeInterval interval = reservation.getTimeInterval();
		RestaurantTable table = reservation.getRestauranttable();
		if (client == null) {
			errors.rejectValue("client", "Value is required", "Value is required");
		}
		if (date == null) {
			errors.rejectValue("date", "Value is required", "Value is required");
		}
		if (interval == null) {
			errors.rejectValue("timeInterval", "Value is required", "Value is required");
		}
		if (table == null) {
			errors.rejectValue("restauranttable", "Value is required", "Value is required");
		}
		/*String name = game.getName();
		Integer maxPlayers = game.getMaxPlayers();
		// name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		if( maxPlayers == null  || maxPlayers == 0) {
			errors.rejectValue("maxPlayers", REQUIRED, REQUIRED);
		}*/
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RestaurantReservation.class.isAssignableFrom(clazz);
	}

	

}
