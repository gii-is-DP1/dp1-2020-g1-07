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

import java.time.LocalDate;
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
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.MenuService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= MenuController.class,
		includeFilters= {@ComponentScan.Filter(value = ShiftFormatter2.class, type = FilterType.ASSIGNABLE_TYPE ),
						@ComponentScan.Filter(value = DishFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
						@ComponentScan.Filter(value = MenuValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
		excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class MenuControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MenuService menuService;
	
	private Menu menu;
	private Dish first_dish;
	private Dish second_dish;
	private Dish dessert;

	@BeforeEach
	void setup() {

		//Preparación de shifts
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
		given(this.menuService.findShifts()).willReturn(shifts);
		
		//Preparacion de platos
		DishCourse dishCourse1 = new DishCourse();
		dishCourse1.setName("First");
		dishCourse1.setId(1);
		DishCourse dishCourse2 = new DishCourse();
		dishCourse2.setName("Second");
		dishCourse2.setId(2);
		DishCourse dishCourse3 = new DishCourse();
		dishCourse3.setName("Dessert");
		dishCourse3.setId(3);

		first_dish = new Dish();
		first_dish.setId(1);
		first_dish.setName("Ensalada");
		first_dish.setShift(shift1);
		first_dish.setDish_course(dishCourse1);
		List<Dish> first_dishes = new ArrayList<Dish>();
		first_dishes.add(first_dish);
		given(this.menuService.findFirstDishes()).willReturn(first_dishes);
		given(this.menuService.findFirstDishesByShift(1)).willReturn(first_dishes);
		second_dish = new Dish();
		second_dish.setId(2);
		second_dish.setName("Filete");
		second_dish.setShift(shift1);
		second_dish.setDish_course(dishCourse2);
		List<Dish> second_dishes = new ArrayList<Dish>();
		second_dishes.add(second_dish);
		given(this.menuService.findSecondDishes()).willReturn(second_dishes);
		given(this.menuService.findSecondDishesByShift(1)).willReturn(second_dishes);
		dessert = new Dish();
		dessert.setId(3);
		dessert.setName("Natilla");
		dessert.setShift(shift1);
		dessert.setDish_course(dishCourse3);
		List<Dish> desserts = new ArrayList<Dish>();
		desserts.add(dessert);
		given(this.menuService.findDesserts()).willReturn(desserts);
		given(this.menuService.findDessertsByShift(1)).willReturn(desserts);
		
		//Preparacion de cocinero
		Cook cook = new Cook();
		List<Dish> prepares = new ArrayList<Dish>();
		prepares.add(first_dish); prepares.add(second_dish); prepares.add(dessert);
		cook.setPrepares(prepares);
		cook.setDni("11122233A");
		Schedule schedule1 = new Schedule();
		schedule1.setDate(LocalDate.of(2020, 12, 10));
		schedule1.setShift(shift1);
		schedule1.setEmp(cook);
		Schedule schedule2 = new Schedule();
		schedule2.setDate(LocalDate.of(2020, 12, 24));
		schedule2.setShift(shift1);
		schedule2.setEmp(cook);
		List<String> dnis = new ArrayList<String>();
		dnis.add("11122233A");
		List<Cook> cooks = new ArrayList<Cook>();
		cooks.add(cook);
		given(this.menuService.findSchedulesDnisByShiftAndDate(shift1,LocalDate.of(2020, 12, 10))).willReturn(dnis);
		given(this.menuService.findSchedulesDnisByShiftAndDate(shift1,LocalDate.of(2020, 12, 24))).willReturn(dnis);
		given(this.menuService.findCooks()).willReturn(cooks);
		
		//Preparacion del menu
		menu = new Menu();
		menu.setId(1);
		menu.setDate(LocalDate.of(2020, 12, 24));
		menu.setShift(shift1);
		menu.setFirst_dish(first_dish);
		menu.setSecond_dish(second_dish);
		menu.setDessert(dessert);
		given(this.menuService.findMenuById(1)).willReturn(Optional.of(menu));
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(menu);
		given(this.menuService.findAll()).willReturn(menus);
		given(this.menuService.findMenusByDate(LocalDate.of(2020, 12, 24))).willReturn(menus);
		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates.add(LocalDate.of(2020, 12, 24));
		given(this.menuService.findAllDates()).willReturn(dates);
	}
	
	//Tests de CreationForm
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/menus/new")).andExpect(status().isOk()).andExpect(model().attributeExists("menu"))
		.andExpect(view().name("menus/addMenu"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/menus/save")
						.with(csrf())
						.param("date", "2020/12/10")
						.param("first_dish", "Ensalada")
						.param("second_dish", "Filete")
						.param("dessert", "Natilla")
						.param("shift", "Day"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("menus/menusList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedDateAndShift() throws Exception {
		mockMvc.perform(post("/menus/save")
						.with(csrf())
						.param("shift", "Day")
						.param("date", "2020/12/24")
						.param("first_dish", "Ensalada")
						.param("second_dish", "Filete")
						.param("dessert", "Natilla"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("menu"))
			.andExpect(model().attributeHasFieldErrors("menu", "date"))
			.andExpect(view().name("menus/addMenu"));
	}
		
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/menus/save")
						.with(csrf())
						.param("shift", "Day")
						.param("date", "2020/12/10")
						.param("dessert", "Natilla"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("menu"))
			.andExpect(model().attributeHasFieldErrors("menu", "first_dish"))
			.andExpect(model().attributeHasFieldErrors("menu", "second_dish"))
			.andExpect(view().name("menus/addMenu"));
	}
	
	//Tests de UpdateForm
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateMenuForm() throws Exception {
		mockMvc.perform(get("/menus/{menuId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("menu"))
				.andExpect(model().attribute("menu", hasProperty("date", is(LocalDate.of(2020, 12, 24)))))
				.andExpect(model().attribute("menu", hasProperty("shift", is(menuService.findShifts().toArray()[0]))))
				.andExpect(model().attribute("menu", hasProperty("first_dish", is(menuService.findFirstDishes().toArray()[0]))))
				.andExpect(model().attribute("menu", hasProperty("second_dish", is(menuService.findSecondDishes().toArray()[0]))))
				.andExpect(model().attribute("menu", hasProperty("dessert", is(menuService.findDesserts().toArray()[0]))))			
				.andExpect(view().name("menus/updateMenu"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateMenuFormSuccess() throws Exception {
		mockMvc.perform(post("/menus/{menuId}/edit", 1)
							.with(csrf())
							.param("date", "2020/12/10")
							.param("first_dish", "Ensalada")
							.param("second_dish", "Filete")
							.param("dessert", "Natilla")
							.param("shift", "Day"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/menus"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateMenuFormHasErrors() throws Exception {
		mockMvc.perform(post("/menus/{menuId}/edit", 1)
							.with(csrf())
							.param("shift", "Day")
							.param("first_dish", "Ensalada")
							.param("second_dish", "Filete")
							.param("dessert", "Natilla"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("menu"))
				.andExpect(model().attributeHasFieldErrors("menu", "date"))
				.andExpect(view().name("menus/updateMenu"));
	}
    
    //Tests de byDay
    
    @WithMockUser(value = "spring")
	@Test
	void testInitByDay() throws Exception{
		mockMvc.perform(get("/menus/byDay")).andExpect(status().isOk()).andExpect(model().attributeExists("dates"))
		.andExpect(view().name("menus/menusByDay"));
	}

}
