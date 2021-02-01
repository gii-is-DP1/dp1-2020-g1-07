package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.stereotype.Component;

@Component
public class TimeIntervalFormatter implements Formatter<TimeInterval> {
	
	private final RestaurantReservationService restaurantReservationService;

	@Autowired
	public TimeIntervalFormatter(RestaurantReservationService restaurantReservationService) {
		this.restaurantReservationService = restaurantReservationService;
	}

	@Override
	public String print(TimeInterval timeInterval, Locale locale) {
		return timeInterval.getName();
	}

	@Override
	public TimeInterval parse(String text, Locale locale) throws ParseException {
		Collection<TimeInterval> findTimeIntervals = this.restaurantReservationService.findTimeIntervals();
		for (TimeInterval timeInterval : findTimeIntervals) {
			if (timeInterval.getName().equals(text)) {
				return timeInterval;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
