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
}
