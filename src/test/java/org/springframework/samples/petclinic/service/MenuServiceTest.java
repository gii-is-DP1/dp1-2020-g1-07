package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MenuServiceTest {

	@Autowired
	protected MenuService menuService;

	@Test
	void shouldFindMenus() {
		List<Menu> menus = StreamSupport.stream(this.menuService.findAll().spliterator(), false).collect(Collectors.toList());

		Menu menu = EntityUtils.getById(menus, Menu.class, 1);
		assertThat(menu.getDate().isEqual(LocalDate.of(2020, 11, 28)));
		assertThat(menu.getFirst_dish().getId()==1);
		assertThat(menu.getSecond_dish().getId()==2);
		assertThat(menu.getDessert().getId()==3);
		assertThat(menu.getShift().getId()).isEqualTo(2);
	}

	@Test
	void shouldFindAllDates() {
		List<LocalDate> dates = StreamSupport.stream(this.menuService.findAllDates().spliterator(), false).collect(Collectors.toList());
		assertThat(dates.size()==1);
		
		LocalDate date_from_find = dates.get(0);
		assertThat(date_from_find.equals(LocalDate.of(2010, 9, 7)));
	}
	
	@Test
	void shouldFirstDishesByShift() {
		List<Dish> first_dishes_day = StreamSupport.stream(this.menuService.findFirstDishesByShift(2).spliterator(), false).collect(Collectors.toList());
		assertThat(first_dishes_day.size()==1);
		
		Dish first_dish = first_dishes_day.get(0);
		assertThat(first_dish.getName().equals("Ensalada Cesar"));
	}
	
	@Test
	void shouldFindSecondDishes() {
		List<Dish> second_dishes = StreamSupport.stream(this.menuService.findSecondDishes().spliterator(), false).collect(Collectors.toList());
		assertThat(second_dishes.size()==1);
		
		Dish second_dish = second_dishes.get(0);
		assertThat(second_dish.getName().equals("Serranito"));
	}
	
	@Test
	void shouldFindShifts() {
		List<Shift> shifts = StreamSupport.stream(this.menuService.findShifts().spliterator(), false).collect(Collectors.toList());
		
		assertThat(shifts.size()==3);
		Shift S1 = EntityUtils.getById(shifts, Shift.class, 1);
		assertThat(S1.getName()).isEqualTo("Day");
		Shift S2 = EntityUtils.getById(shifts, Shift.class, 2);
		assertThat(S2.getName()).isEqualTo("Afternoon");
		Shift S3 = EntityUtils.getById(shifts, Shift.class, 3);
		assertThat(S3.getName()).isEqualTo("Night");
	}
	
	@Test
	void shouldFindMenusByDate() {
		LocalDate date = LocalDate.of(2010, 9, 7);
		List<Menu> menus = StreamSupport.stream(this.menuService.findMenusByDate(date).spliterator(), false).collect(Collectors.toList());
		assertThat(menus.size()==1);
	}
	
	/*@Test
	void shouldFindDishes() {
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		
		Dish dish = EntityUtils.getById(dishes, Dish.class, 2);
		assertThat(dish.getName()).isEqualTo("Serranito");
		assertThat(dish.getShift().getId()).isEqualTo(2);
		assertThat(dish.getDish_course().getId()).isEqualTo(2);
	}*/
	
}
