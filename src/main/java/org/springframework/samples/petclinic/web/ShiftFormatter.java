package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.stereotype.Component;

@Component
public class ShiftFormatter implements Formatter<Shift>  {
	
	private final DishService dishService;
	
	@Autowired
	public ShiftFormatter(DishService dishService) {
		this.dishService = dishService;
	}

	@Override
	public String print(Shift shift, Locale locale) {
		return shift.getName();
	}

	@Override
	public Shift parse(String text, Locale locale) throws ParseException {
		Collection<Shift> findShifts = this.dishService.findShifts();
		for (Shift shift : findShifts) {
			if (shift.getName().equals(text)) {
				return shift;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
