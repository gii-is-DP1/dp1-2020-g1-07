package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

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
	
	@Test
	void testFindDishCourses() {
		List<DishCourse> dishCourses = StreamSupport.stream(this.dishService.findDishCourses().spliterator(), false).collect(Collectors.toList());
		
		assertThat(dishCourses.size()==3);
		DishCourse DC1 = EntityUtils.getById(dishCourses, DishCourse.class, 1);
		assertThat(DC1.getName()).isEqualTo("First");
		DishCourse DC2 = EntityUtils.getById(dishCourses, DishCourse.class, 2);
		assertThat(DC2.getName()).isEqualTo("Second");
		DishCourse DC3 = EntityUtils.getById(dishCourses, DishCourse.class, 3);
		assertThat(DC3.getName()).isEqualTo("Dessert");
	}
	
	@Test
	void testFindShifts() {
		List<Shift> shifts = StreamSupport.stream(this.dishService.findShifts().spliterator(), false).collect(Collectors.toList());
		
		assertThat(shifts.size()==3);
		Shift S1 = EntityUtils.getById(shifts, Shift.class, 1);
		assertThat(S1.getName()).isEqualTo("Day");
		Shift S2 = EntityUtils.getById(shifts, Shift.class, 2);
		assertThat(S2.getName()).isEqualTo("Afternoon");
		Shift S3 = EntityUtils.getById(shifts, Shift.class, 3);
		assertThat(S3.getName()).isEqualTo("Night");
	}
	
	@Test
	void testAddingDuck() {
		Dish new_dish = new Dish();
		new_dish.setName("Duck a l'orange");
		List<Shift> shifts = StreamSupport.stream(this.dishService.findShifts().spliterator(), false).collect(Collectors.toList());
		new_dish.setShift(shifts.get(2)); //Night
		List<DishCourse> dishCourses = StreamSupport.stream(this.dishService.findDishCourses().spliterator(), false).collect(Collectors.toList());
		new_dish.setDish_course(dishCourses.get(1)); //Second
		dishService.save(new_dish);
		
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		Dish saved_dish = dishes.get(3);
		assertThat(saved_dish.getName()).isEqualTo("Duck a l'orange");
		assertThat(saved_dish.getShift().getName()).isEqualTo("Night");
		assertThat(saved_dish.getDish_course().getName()).isEqualTo("Second");
	}
	
	@Test
	void testAddingDishThatExists() throws Exception{
		Dish new_dish = new Dish();
		new_dish.setName("Serranito");
		List<Shift> shifts = StreamSupport.stream(this.dishService.findShifts().spliterator(), false).collect(Collectors.toList());
		new_dish.setShift(shifts.get(2)); //Night
		List<DishCourse> dishCourses = StreamSupport.stream(this.dishService.findDishCourses().spliterator(), false).collect(Collectors.toList());
		new_dish.setDish_course(dishCourses.get(1)); //Second
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {dishService.save(new_dish);;});
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


