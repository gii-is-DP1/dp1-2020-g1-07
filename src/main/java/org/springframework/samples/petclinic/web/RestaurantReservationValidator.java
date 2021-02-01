package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.SlotGainService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RestaurantReservationValidator implements Validator{
private static final String REQUIRED = "required";
	
	@Autowired
	private RestaurantReservationService restaurantReservationService;
	
	public Boolean getRestaurantReservationwithIdDifferent(RestaurantReservation RS) {
		Boolean result = false;
		List<RestaurantReservation> restaurantReservations = StreamSupport.stream(this.restaurantReservationService.findAll().spliterator(), false).collect(Collectors.toList());
		for (RestaurantReservation restaurantReservation : restaurantReservations) {
			if (restaurantReservation.getRestauranttable().getId().equals(RS.getId()) && restaurantReservation.getDate().isEqual(RS.getDate())) {
				result = true;
			}
		}
		return result;
	}
	
	public Boolean getRestaurantReservationwithIdDifferent(RestaurantReservation RS, Integer id) {
		Boolean result = false;
		List<RestaurantReservation> restaurantReservations = StreamSupport.stream(this.restaurantReservationService.findAll().spliterator(), false).collect(Collectors.toList());
		for (RestaurantReservation restaurantReservation : restaurantReservations) {
			if (restaurantReservation.getRestauranttable().getId().equals(RS.getId()) && restaurantReservation.getDate().isEqual(RS.getDate()) && id!=restaurantReservation.getId()) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RestaurantReservation.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RestaurantReservation restaurantReservation = (RestaurantReservation) target;
		LocalDate date = restaurantReservation.getDate();
		RestaurantTable restT = restaurantReservation.getRestauranttable();
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		if(restT == null) {
			errors.rejectValue("restaurantTable", REQUIRED, REQUIRED);
		}
	}
}
