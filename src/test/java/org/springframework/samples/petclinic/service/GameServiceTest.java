package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.repository.GameRepository;


@ExtendWith(MockitoExtension.class)
class GameServiceTest {

	@Mock
	private GameRepository gameRepo;
	
	protected GameService gameService;
	
	@BeforeEach
    void setup() {
		gameService = new GameService(gameRepo);
    }
	@Test
	public void testCountWithInitialData() {
		int count= gameService.gameCount();
		assertEquals(count,0);
	}
	@Test
	void testAddingPoker() {
		Game game = new Game();
		game.setName("Poker");
		GameType gametype = new GameType();
		gametype.setName("Cards");
		game.setGametype(gametype);
		game.setMaxPlayers(6);
		Collection<Game> sampleGames = new ArrayList<Game>();
		sampleGames.add(game);
        when(gameRepo.findAll()).thenReturn(sampleGames);
		
        
		List<Game> games = StreamSupport.stream(this.gameService.findAll().spliterator(), 
				false).collect(Collectors.toList());
		
		Game saved_game = games.get(0);
		assertTrue(saved_game.getName().equals("Poker"));
		assertTrue(saved_game.getGametype().getName().equals("Cards")); 
		assertTrue(saved_game.getMaxPlayers() == 6);
		
	}
}
