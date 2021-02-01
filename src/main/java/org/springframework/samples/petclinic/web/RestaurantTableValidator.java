package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.RestaurantTableService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RestaurantTableValidator implements Validator{
	private static final String REQUIRED = "required";
	
	@Autowired
	private RestaurantTableService restaurantTableService;
	
	public Boolean getRestaurantTablewithIdDifferent(RestaurantTable RT) {
		Boolean result = false;
		List<RestaurantTable> restaurantTables = StreamSupport.stream(this.restaurantTableService.findAll().spliterator(), false).collect(Collectors.toList());
		for (RestaurantTable restaurantTable : restaurantTables) {
			if (restaurantTable.getId().equals(RT.getId())) {
				result = true;
			}
		}
		return result;
	}
	
	public Boolean getRestaurantTablewithIdDifferent(RestaurantTable RT, Integer id) {
		Boolean result = false;
		List<RestaurantTable> restaurantTables = StreamSupport.stream(this.restaurantTableService.findAll().spliterator(), false).collect(Collectors.toList());
		for (RestaurantTable restaurantTable : restaurantTables) {
			if ((restaurantTable.getId().equals(RT.getId()) && (id!=restaurantTable.getId()))) {
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
