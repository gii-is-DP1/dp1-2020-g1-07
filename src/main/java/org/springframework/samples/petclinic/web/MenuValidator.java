package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.MenuService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MenuValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Autowired
	private MenuService menuService;

	public Menu getMenuwithIdDifferent(Shift shift, LocalDate date, Integer id) {
		Menu empty_menu = null;
		List<Menu> menus = StreamSupport.stream(this.menuService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Menu menu : menus) {
			if (menu.getShift().getName().equals(shift.getName()) && menu.getDate().isEqual(date) && id!=menu.getId()) {
				return menu;
			}
		}
		return empty_menu;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Menu menu = (Menu) obj;
		LocalDate date = menu.getDate();
		Shift shift = menu.getShift();
		Dish first_dish = menu.getFirst_dish();
		Dish second_dish = menu.getSecond_dish();
		Dish dessert = menu.getDessert();
		Menu existing_menu = null;
		if(shift!=null && date!=null) existing_menu = getMenuwithIdDifferent(shift,date,menu.getId());
		// Date validation
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		// Dishes validation
		if(first_dish==null || first_dish.getName().trim().equals("") || shift.getId() != first_dish.getShift().getId()) {
			errors.rejectValue("first_dish", REQUIRED + " that the dish is not in the selected shift", REQUIRED + " that the dish is not in the selected shift");
		}
		if(second_dish==null || first_dish.getName().trim().equals("") || shift.getId() != second_dish.getShift().getId()) {
			errors.rejectValue("second_dish", REQUIRED + " that the dish is not in the selected shift", REQUIRED + " that the dish is not in the selected shift");
		}
		if(dessert==null || dessert.getName().trim().equals("") || shift.getId() != dessert.getShift().getId()) {
			errors.rejectValue("dessert", REQUIRED + " that the dish is not in the selected shift", REQUIRED + " that the dish is not in the selected shift");
		}
		//Already existing menu validation
		if(existing_menu != null) {
			errors.rejectValue("date", "Ya existe un menu para este turno y esta fecha", "Ya existe un menu para este turno y esta fecha");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Menu.class.isAssignableFrom(clazz);
	}

}
