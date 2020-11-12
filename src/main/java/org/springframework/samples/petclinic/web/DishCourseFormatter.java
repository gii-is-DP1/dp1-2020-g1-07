package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.stereotype.Component;

@Component
public class DishCourseFormatter implements Formatter<DishCourse>  {
	
	private final DishService dishService;

	@Autowired
	public DishCourseFormatter(DishService dishService) {
		this.dishService = dishService;
	}

	@Override
	public String print(DishCourse dishCourse, Locale locale) {
		return dishCourse.getName();
	}

	@Override
	public DishCourse parse(String text, Locale locale) throws ParseException {
		Collection<DishCourse> findDishCourses = this.dishService.findDishCourses();
		for (DishCourse dishCourse : findDishCourses) {
			if (dishCourse.getName().equals(text)) {
				return dishCourse;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
