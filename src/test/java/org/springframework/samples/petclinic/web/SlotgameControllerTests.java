package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.service.SlotgameService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= SlotgameController.class,
			includeFilters= @ComponentScan.Filter(value = SlotgameValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
			excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class SlotgameControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SlotgameService slotgameService;
	
	private Slotgame slotgame;

	@BeforeEach
	void setup() {
			
		//Preparacion del slotgamre
		slotgame = new Menu();
		slotgame.setId(1);
		slotgame.setDate(LocalDate.of(2020, 12, 24));
		slotgame.setShift(shift1);
		slotgame.setFirst_dish(first_dish);
		slotgame.setSecond_dish(second_dish);
		slotgame.setDessert(dessert);
		given(this.menuService.findMenuById(1)).willReturn(Optional.of(slotgame));
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(slotgame);
		given(this.menuService.findAll()).willReturn(menus);
		given(this.menuService.findMenusByDate(LocalDate.of(2020, 12, 24))).willReturn(menus);
		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates.add(LocalDate.of(2020, 12, 24));
		given(this.menuService.findAllDates()).willReturn(dates);
	}

}
