package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTableFormatter implements Formatter<RestaurantTable>  {
	
	private final RestaurantReservationService restaurantReservationService;

	@Autowired
	public RestaurantTableFormatter(RestaurantReservationService restaurantReservationService) {
		this.restaurantReservationService = restaurantReservationService;
	}

	@Override
	public String print(RestaurantTable restaurantTable, Locale locale) {
		return restaurantTable.getId().toString();
	}

	@Override
	public RestaurantTable parse(String text, Locale locale) throws ParseException {
		Collection<RestaurantTable> findRestaurantTables = this.restaurantReservationService.findRestaurantTables();
		for (RestaurantTable restaurantTable : findRestaurantTables) {
			if (restaurantTable.getId()==Integer.valueOf(text)) {
				return restaurantTable;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}
	
	

}
