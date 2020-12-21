package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SlotGameServiceTests {
	
	@Autowired
	protected SlotgameService slotGameService;
	
	@Test
	public void testCountWithInitialData() {
		int count= slotGameService.slotGameCount();
		assertEquals(count,3);
	}
	
	//INSERT INTO slotgames VALUES (2,'The Atlantis Treasure',0);

	@Test
	void shouldFindSlotGames() {
		List<Slotgame> slotgames = StreamSupport.stream(this.slotGameService.findAll().spliterator(), false).collect(Collectors.toList());
		
		Slotgame slotGame = EntityUtils.getById(slotgames, Slotgame.class, 2);
		assertThat(slotGame.getName()).isEqualTo("The Atlantis Treasure");
		assertThat(slotGame.getJackpot()==0);
	}
	
	/*@Test
	void testAddingNewSlotGame() {
		SlotGame new_game = new SlotGame();
		new_game.setName("Duck a l'orange");
		List<Shift> shifts = StreamSupport.stream(this.dishService.findShifts().spliterator(), false).collect(Collectors.toList());
		new_game.setShift(shifts.get(2)); //Night
		List<DishCourse> dishCourses = StreamSupport.stream(this.dishService.findDishCourses().spliterator(), false).collect(Collectors.toList());
		new_game.setDish_course(dishCourses.get(1)); //Second
		dishService.save(new_game);
		
		List<Dish> dishes = StreamSupport.stream(this.dishService.findAll().spliterator(), false).collect(Collectors.toList());
		Dish saved_dish = dishes.get(dishes.size()-1);
		assertThat(saved_dish.getName().equals("Duck a l'orange"));
		assertThat(saved_dish.getShift().getName().equals("Night"));
		assertThat(saved_dish.getDish_course().getName().equals("Second"));
	}*/
}
