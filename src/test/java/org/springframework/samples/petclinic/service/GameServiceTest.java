package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class GameServiceTest {

	@Autowired
	private GameService gameService;
	
	/*@Test
	public void testCountWithInitialData() {
		int count= casTabService.casinoTableCount();
		assertEquals(count,1);
	}*/
	@Test
	void shouldFineGame() {
		List<Game> games = StreamSupport.stream(this.gameService.findAll().spliterator(), false).collect(Collectors.toList());

		Game game = EntityUtils.getById(games, Game.class, 1);
		assertThat(game.getName()).isEqualTo("Poker");
		assertThat(game.getMaxPlayers()).isEqualTo(8);
		assertThat(game.getGametype().getId()).isEqualTo(2);
	}	

}
