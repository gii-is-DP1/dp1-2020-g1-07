package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.MenuService;
import org.springframework.stereotype.Component;

@Component
public class ShiftFormatter2 implements Formatter<Shift>{
	private final MenuService menuService;
	
	@Autowired
	public ShiftFormatter2(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public String print(Shift shift, Locale locale) {
		return shift.getName();
	}

	@Override
	public Shift parse(String text, Locale locale) throws ParseException {
		Collection<Shift> findShifts = this.menuService.findShifts();
		for (Shift shift : findShifts) {
			if (shift.getName().equals(text)) {
				return shift;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}
}
