package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.repository.DishRepository;

@ExtendWith(MockitoExtension.class)
public class DishServiceTests {
	
	@Mock
	private DishRepository dishRepo;
	
	protected DishService dishService;
	
	@BeforeEach
    void setup() {
		dishService = new DishService(dishRepo);
    }
	
	@Test
	public void testCountWithInitialData() {
		int count= dishService.dishCount();
		assertEquals(count,0);
	}
	
	@Test
	void testAddingDuck() {
		Dish new_dish = new Dish();
		new_dish.setName("Duck a l'orange");
		Shift shift = new Shift();
		shift.setName("Night");
		new_dish.setShift(shift);
		DishCourse dishCourse = new DishCourse();
		dishCourse.setName("Second");
		new_dish.setDish_course(dishCourse);
		Collection<Dish> sampleDishes = new ArrayList<Dish>();
		sampleDishes.add(new_dish);
        when(dishRepo.findAll()).thenReturn(sampleDishes);
		
        
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		Dish saved_dish = dishes.get(dishes.size()-1);
		assertThat(saved_dish.getName().equals("Duck a l'orange"));
		assertThat(saved_dish.getShift().getName().equals("Night"));
		assertThat(saved_dish.getDish_course().getName().equals("Second"));
	}
	
	@Test
	void testAddingDishThatExists() throws Exception{
		Dish new_dish = new Dish();
		new_dish.setName("Serranito");
		Shift shift = new Shift();
		shift.setName("Night");
		new_dish.setShift(shift);
		DishCourse dishCourse = new DishCourse();
		dishCourse.setName("Second");
		new_dish.setDish_course(dishCourse); //Second
		dishService.save(new_dish);
		when(dishRepo.save(new_dish)).thenThrow(DataIntegrityViolationException.class);
		
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {dishService.save(new_dish);});
	}
	
	/*@Test
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


