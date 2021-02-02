package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.samples.petclinic.service.MenuService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DishValidator implements Validator {

	private static final String REQUIRED = "required";

	@Autowired
	private DishService dishService;	
	
	@Autowired
	private MenuService menuService;
	
	//For creating
		public boolean getDishwithIdDifferent(String name) {
			name = name.toLowerCase();
			Boolean result = false;
			List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
			for (Dish dish : dishes) {
				String compName = dish.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					result = true;
					break;
				}
			}
			return result;
		}
	
	//For editing
	public boolean getDishwithIdDifferent(String name, Integer id) {
		name = name.toLowerCase();
		Boolean result = false;
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Dish dish : dishes) {
			String compName = dish.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && id!=dish.getId()) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public boolean isUsedInMenu(Dish dish) {
		boolean result = false;
		List<Menu> menus = StreamSupport.stream(this.menuService.findAll().spliterator(), false).collect(Collectors.toList());
		for(Menu menu: menus) {
			String dishName = dish.getName();
			String FD = menu.getFirst_dish().getName();
			String SD = menu.getSecond_dish().getName();
			String D = menu.getDessert().getName();
			if(dishName.equals(FD) || dishName.equals(SD) || dishName.equals(D)) result = true;
		}
		return result;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Dish dish = (Dish) obj;
		String name = dish.getName();
		DishCourse dc = dish.getDish_course();
		Shift shift = dish.getShift();
		// name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		// dish_course validation
		if (dc == null || dc.getName().trim().equals("")) {
			errors.rejectValue("dish_course", REQUIRED, REQUIRED);
		}
		// shift validation
		if (shift == null || shift.getName().trim().equals("")) {
			errors.rejectValue("shift", REQUIRED, REQUIRED);
		}		
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Dish.class.isAssignableFrom(clazz);
	}
	

	

}
