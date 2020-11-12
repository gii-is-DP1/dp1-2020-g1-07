package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.service.MenuService;
import org.springframework.stereotype.Component;

@Component
public class DishFormatter implements Formatter<Dish>{
	private final MenuService menuService;

	@Autowired
	public DishFormatter(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public String print(Dish dish, Locale locale) {
		return dish.getName();
	}

	@Override
	public Dish parse(String text, Locale locale) throws ParseException {
		Collection<Dish> findDishes = this.menuService.findDishes();
		for (Dish dish : findDishes) {
			if (dish.getName().equals(text)) {
				return dish;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}
}
