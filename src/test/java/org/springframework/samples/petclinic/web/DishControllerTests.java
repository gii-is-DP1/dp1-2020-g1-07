package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= DishController.class,
		includeFilters= {@ComponentScan.Filter(value = DishCourseFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
						@ComponentScan.Filter(value = ShiftFormatter.class, type = FilterType.ASSIGNABLE_TYPE )},
		excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class DishControllerTests {
		
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DishService dishService;
	
	private Dish dish;

	@BeforeEach
	void setup() {

		//Hay que preparar los dos enumerados y despues un plato de prueba
		Shift shift1 = new Shift();
		shift1.setName("Day");
		shift1.setId(1);
		Shift shift2 = new Shift();
		shift2.setName("Afternoon");
		shift2.setId(2);
		Shift shift3 = new Shift();
		shift3.setName("Night");
		shift3.setId(3);
		List<Shift> shifts = new ArrayList<Shift>();
		shifts.add(shift1);
		shifts.add(shift2);
		shifts.add(shift3);
		given(this.dishService.findShifts()).willReturn(shifts);
		
		DishCourse dishCourse1 = new DishCourse();
		dishCourse1.setName("First");
		dishCourse1.setId(1);
		DishCourse dishCourse2 = new DishCourse();
		dishCourse2.setName("Second");
		dishCourse2.setId(2);
		DishCourse dishCourse3 = new DishCourse();
		dishCourse3.setName("Dessert");
		dishCourse3.setId(3);
		List<DishCourse> dishCourses = new ArrayList<DishCourse>();
		dishCourses.add(dishCourse1);
		dishCourses.add(dishCourse2);
		dishCourses.add(dishCourse3);
		given(this.dishService.findDishCourses()).willReturn(dishCourses);
		
		dish = new Dish();
		dish.setId(1);
		dish.setName("Espagueti");
		dish.setShift(shift3);
		dish.setDish_course(dishCourse2);
		given(this.dishService.findDishById(1)).willReturn(Optional.of(dish));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/dishes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("dish"))
		.andExpect(view().name("dishes/addDish"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/dishes/save").param("name", "Macarrones")
						.with(csrf())
						.param("dish_course", "First")
						.param("shift", "Day"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("dishes/dishesList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedName() throws Exception {
		mockMvc.perform(post("/dishes/save").param("name", "Espagueti")
						.with(csrf())
						.param("dish_course", "First")
						.param("shift", "Day"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("dishes/dishesList"));
		mockMvc.perform(post("/dishes/save").param("name", "Espagueti")
				.with(csrf())
				.param("dish_course", "First")
				.param("shift", "Day"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(view().name("dishes/dishesList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/dishes/save")
						.with(csrf())
						.param("dish_course", "First")
						.param("shift", "Day"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("dish"))
			.andExpect(model().attributeHasFieldErrors("dish", "name"))
			.andExpect(view().name("dishes/addDish"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateDishForm() throws Exception {
		mockMvc.perform(get("/dishes/{dishId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("dish"))
				.andExpect(model().attribute("dish", hasProperty("name", is("Espagueti"))))
				.andExpect(model().attribute("dish", hasProperty("dish_course", is(dishService.findDishCourses().toArray()[1]))))
				.andExpect(model().attribute("dish", hasProperty("shift", is(dishService.findShifts().toArray()[2]))))
				.andExpect(view().name("dishes/updateDish"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateDishFormSuccess() throws Exception {
		mockMvc.perform(post("/dishes/{dishId}/edit", 1)
							.with(csrf())
							.param("name", "Macarrones")
							.param("dish_course", "First")
							.param("shift", "Day"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/dishes"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform(post("/dishes/{dishId}/edit", 1)
							.with(csrf())
							.param("dish_course", "First")
							.param("shift", "Day"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("dish"))
				.andExpect(model().attributeHasFieldErrors("dish", "name"))
				.andExpect(view().name("dishes/updateDish"));
	}
	
}

