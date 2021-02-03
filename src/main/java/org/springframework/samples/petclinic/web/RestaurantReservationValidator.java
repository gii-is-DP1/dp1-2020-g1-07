package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RestaurantReservationValidator implements Validator{

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object target, Errors errors) {
		RestaurantReservation restaurantReservation = (RestaurantReservation) target;
		LocalDate date = restaurantReservation.getDate();
		RestaurantTable restT = restaurantReservation.getRestauranttable();
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		if(restT == null) {
			errors.rejectValue("restauranttable", REQUIRED, REQUIRED);
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RestaurantReservation.class.isAssignableFrom(clazz);
	}

}
