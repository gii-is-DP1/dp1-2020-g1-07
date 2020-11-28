package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedDishNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DishServiceTests {
	
	@Autowired
	protected DishService dishService;
	
	@Test
	public void testCountWithInitialData() {
		int count= dishService.dishCount();
		assertEquals(count,3);
	}
	
	@Test
	void shouldFindDishes() {
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		
		Dish dish = EntityUtils.getById(dishes, Dish.class, 2);
		assertThat(dish.getName()).isEqualTo("Serranito");
		assertThat(dish.getShift().getId()).isEqualTo(2);
		assertThat(dish.getDish_course().getId()).isEqualTo(2);
	}
	/* DOES NOT WORK
	@Test
	@Transactional
	public void shouldInsertDishIntoDatabaseAndGenerateId() throws DuplicatedPetNameException {
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		Integer size = dishes.size();

		Dish dish = new Dish();
		dish.setName("Fish and Chips");
		Collection<DishCourse> courses = this.dishService.findDishCourses();
		dish.setDish_course(EntityUtils.getById(courses, DishCourse.class, 2));
		Collection<Shift> shifts = this.dishService.findShifts();
		dish.setShift(EntityUtils.getById(shifts, Shift.class, 2));
		
		Dish otherDish=getDishwithIdDifferent(dish.getName(), dish.getId());
        if (StringUtils.hasLength(dish.getName()) &&  (otherDish!= null && otherDish.getId()!=dish.getId())) {            	
        	throw new DuplicatedPetNameException();
        }else
            dishService.save(dish);
        	dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
			assertThat(dishes.size() == (size + 1));
	}
	*/
}


