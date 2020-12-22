package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.repository.MenuRepository;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

	@Mock
	private MenuRepository menuRepo;
	
	protected MenuService menuService;

	@BeforeEach
    void setup() {
		menuService = new MenuService(menuRepo);
    }
	
	@Test
	void testAddingNewMenu() {
		Menu menu = new Menu();
		menu.setName("New menu");
		menu.setDate(LocalDate.of(2020, 9, 11));
		Shift shift = new Shift();
		shift.setName("Day");
		menu.setShift(shift);
		menu.setFirst_dish(generateDummyFirstDish()); //Ensalada
		menu.setSecond_dish(generateDummySecondDish()); //Filete
		menu.setDessert(generateDummyDessert()); //Flan
		Collection<Menu> sampleMenus = new ArrayList<Menu>();
		sampleMenus.add(menu);
        when(menuRepo.findAll()).thenReturn(sampleMenus);
		
		List<Menu> menus = StreamSupport.stream(this.menuService.findAll().spliterator(), false).collect(Collectors.toList());
		Menu saved_menu = menus.get(menus.size()-1);
		assertThat(saved_menu.getDate().getDayOfMonth()==11);
		assertThat(saved_menu.getDate().getMonthValue()==9);
		assertThat(saved_menu.getDate().getYear()==2020);
		assertThat(saved_menu.getShift().getName().equals("Day"));
		assertThat(saved_menu.getFirst_dish().getName().equals("Ensalada"));
		assertThat(saved_menu.getSecond_dish().getName().equals("Filete"));
		assertThat(saved_menu.getDessert().getName().equals("Flan"));
	}
	
	@Test
	void testCatchingMenusByDate() {
		Menu menu = new Menu();
		menu.setName("New menu");
		LocalDate date = LocalDate.of(2020, 9, 11);
		menu.setDate(date);
		Shift shift = new Shift();
		shift.setName("Day");
		menu.setShift(shift);
		menu.setFirst_dish(generateDummyFirstDish()); //Ensalada
		menu.setSecond_dish(generateDummySecondDish()); //Filete
		menu.setDessert(generateDummyDessert()); //Flan
		List<Menu> sampleMenus = new ArrayList<Menu>();
		sampleMenus.add(menu);
        when(menuRepo.findMenusByDate(date)).thenReturn(sampleMenus);
		
		List<Menu> menus = StreamSupport.stream(this.menuService.findMenusByDate(date).spliterator(), false).collect(Collectors.toList());
		Menu saved_menu = menus.get(menus.size()-1);
		assertThat(saved_menu.getDate().getDayOfMonth()==11);
		assertThat(saved_menu.getDate().getMonthValue()==9);
		assertThat(saved_menu.getDate().getYear()==2020);
		assertThat(saved_menu.getShift().getName().equals("Day"));
		assertThat(saved_menu.getFirst_dish().getName().equals("Ensalada"));
		assertThat(saved_menu.getSecond_dish().getName().equals("Filete"));
		assertThat(saved_menu.getDessert().getName().equals("Flan"));
	}
	
	@Test
	void testObtainingDates() {
		Menu menu = new Menu();
		menu.setName("New menu");
		LocalDate date = LocalDate.of(2020, 9, 11);
		menu.setDate(date);
		Shift shift = new Shift();
		shift.setName("Day");
		menu.setShift(shift);
		menu.setFirst_dish(generateDummyFirstDish()); //Ensalada
		menu.setSecond_dish(generateDummySecondDish()); //Filete
		menu.setDessert(generateDummyDessert()); //Flan
		menuRepo.save(menu);
		List<LocalDate> sampleDates = new ArrayList<LocalDate>();
		sampleDates.add(date);
        when(menuRepo.findAllDates()).thenReturn(sampleDates);
		
		List<LocalDate> dates = StreamSupport.stream(this.menuService.findAllDates().spliterator(), false).collect(Collectors.toList());
		LocalDate saved_date = dates.get(dates.size()-1);
		assertThat(saved_date.getDayOfMonth()==11);
		assertThat(saved_date.getMonthValue()==9);
		assertThat(saved_date.getYear()==2020);
	}
		
	public Dish generateDummyFirstDish(){
		Dish new_dish = new Dish();
		new_dish.setName("Ensalada");
		Shift shift = new Shift();
		shift.setName("Day");
		new_dish.setShift(shift);
		DishCourse dishCourse = new DishCourse();
		dishCourse.setName("First");
		new_dish.setDish_course(dishCourse);
		return new_dish;
	}
	
	public Dish generateDummySecondDish(){
		Dish new_dish = new Dish();
		new_dish.setName("Filete");
		Shift shift = new Shift();
		shift.setName("Day");
		new_dish.setShift(shift);
		DishCourse dishCourse = new DishCourse();
		dishCourse.setName("Second");
		new_dish.setDish_course(dishCourse);
		return new_dish;
	}
	
	public Dish generateDummyDessert(){
		Dish new_dish = new Dish();
		new_dish.setName("Flan");
		Shift shift = new Shift();
		shift.setName("Day");
		new_dish.setShift(shift);
		DishCourse dishCourse = new DishCourse();
		dishCourse.setName("Dessert");
		new_dish.setDish_course(dishCourse);
		return new_dish;
	}
	
	/*@Test
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
		assertThat(S1.getName().equals("Day"));
		Shift S2 = EntityUtils.getById(shifts, Shift.class, 2);
		assertThat(S2.getName().equals("Afternoon"));
		Shift S3 = EntityUtils.getById(shifts, Shift.class, 3);
		assertThat(S3.getName().equals("Night"));
	}
	  
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
	void shouldFindDishes() {
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		
		Dish dish = EntityUtils.getById(dishes, Dish.class, 2);
		assertThat(dish.getName()).isEqualTo("Serranito");
		assertThat(dish.getShift().getId()).isEqualTo(2);
		assertThat(dish.getDish_course().getId()).isEqualTo(2);
	}
	
	@Test
	void testAddingNewMenuWithoutDate() {
		Menu menu = new Menu();
		menu.setName("New menu");
		List<Shift> shifts = StreamSupport.stream(this.menuService.findShifts().spliterator(), false).collect(Collectors.toList());
		menu.setShift(shifts.get(1)); //Afternoon
		List<Dish> firstDishes = StreamSupport.stream(this.menuService.findFirstDishesByShift(2).spliterator(), false).collect(Collectors.toList());
		menu.setFirst_dish(firstDishes.get(0)); //Ensalada Cesar
		List<Dish> secondDishes = StreamSupport.stream(this.menuService.findSecondDishesByShift(2).spliterator(), false).collect(Collectors.toList());
		menu.setSecond_dish(secondDishes.get(0)); //Serranito
		List<Dish> desserts = StreamSupport.stream(this.menuService.findDessertsByShift(2).spliterator(), false).collect(Collectors.toList());
		menu.setDessert(desserts.get(0)); //Flan Potax
		Assertions.assertThrows(ConstraintViolationException.class, () -> {menuService.save(menu);});
	}
	
	@Test
	void shouldFindAllDates() {
		List<LocalDate> dates = StreamSupport.stream(this.menuService.findAllDates().spliterator(), false).collect(Collectors.toList());
		assertThat(dates.size()==1);
		
		LocalDate date_from_find = dates.get(0);
		assertThat(date_from_find.equals(LocalDate.of(2010, 9, 7)));
	}
	
	@Test
	void shouldFindMenusByDate() {
		LocalDate date = LocalDate.of(2010, 9, 7);
		List<Menu> menus = StreamSupport.stream(this.menuService.findMenusByDate(date).spliterator(), false).collect(Collectors.toList());
		assertThat(menus.size()==1);
	}
	*/
}
