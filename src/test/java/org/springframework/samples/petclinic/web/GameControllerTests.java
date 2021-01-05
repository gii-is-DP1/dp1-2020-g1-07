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
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.samples.petclinic.service.GameService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= GameController.class,
includeFilters= {@ComponentScan.Filter(value = ShowTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE ), 
				//@ComponentScan.Filter(value = 	ArtistFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = GameValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class GameControllerTests {
	private Game game;
	private Game game2;
	private Game game3;
	private Game game4;
	private Game game5;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GameService gameService;
	
	@MockBean 
	private CasinotableService casinotableService;
	@BeforeEach
	void setup() {
		//Enumerates: Gametype
		GameType gametype = new GameType();
		gametype.setId(1);
		gametype.setName("Roulette");
		GameType gametype2 = new GameType();
		gametype2.setId(2);
		gametype2.setName("Cards");
		GameType gametype3 = new GameType();
		gametype3.setId(3);
		gametype3.setName("Dices");
		List<GameType> gametypes = new ArrayList<GameType>();
		gametypes.add(gametype);
		gametypes.add(gametype2);
		gametypes.add(gametype3);
		given(this.gameService.findGameTypes()).willReturn(gametypes);
		
		game = new Game();
		game.setId(1);
		game.setName("Poker");
		game.setMaxPlayers(8);
		game.setGametype(gametype2);

		game2 = new Game();
		game2.setId(2);
		game2.setName("BlackJack");
		game2.setMaxPlayers(6);
		game2.setGametype(gametype2);
		
		game3 = new Game();
		game3.setId(3);
		game3.setName("Texas Hold em");
		game3.setMaxPlayers(8);
		game3.setGametype(gametype2);

		game4 = new Game();
		game4.setId(4);
		game4.setName("Crazy Dices");
		game4.setMaxPlayers(4);
		game4.setGametype(gametype3);
		
		game5 = new Game();
		game5.setId(5);
		game5.setName("Fortune Roulette");
		game5.setMaxPlayers(9);
		game5.setGametype(gametype);
		
		List<Game> games = new ArrayList<Game>();
		games.add(game);
		games.add(game2);
		games.add(game3);
		games.add(game4);
		games.add(game5);
		given(this.gameService.findAll()).willReturn(games);
		given(this.gameService.findGameById(1)).willReturn(Optional.of(game));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/games/new")).andExpect(status().isOk()).andExpect(model().attributeExists("game"))
		.andExpect(view().name("games/addGame"));
	}
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/games/save").param("name", "Poker 2")
						.with(csrf())
						.param("maxPlayers", "8")
						.param("gametype", "Cards"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("games/listGame"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/games/save").param("name", "Poker 2")
						.with(csrf())
						.param("gametype", "Cards"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("game"))
			.andExpect(model().attributeHasFieldErrors("game", "maxPlayers"))
			.andExpect(view().name("games/addGame"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedName() throws Exception {
		mockMvc.perform(post("/games/save").param("name", "Poker")
						.with(csrf())
						.param("maxPlayers", "8")
						.param("gametype", "Cards"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("game"))
			.andExpect(model().attributeHasFieldErrors("game", "name"))
			.andExpect(view().name("games/addGame"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateGameForm() throws Exception {
		mockMvc.perform(get("/games/{gameId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("game"))
				.andExpect(model().attribute("game", hasProperty("name", is(gameService.findGameById(1).get().getName()))))
				.andExpect(model().attribute("game", hasProperty("maxPlayers", is(gameService.findGameById(1).get().getMaxPlayers()))))
				.andExpect(model().attribute("game", hasProperty("gametype", is(gameService.findGameTypes().toArray()[1]))))
				.andExpect(view().name("games/updateGame"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateGameFormSuccess() throws Exception {
		mockMvc.perform(post("/games/{gameId}/edit", 1)
							.with(csrf())
							.param("name", "Poker")
							.param("maxPlayers", "6")
							.param("gametype", "Cards"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/games"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateGameFormHasErrors() throws Exception {
		mockMvc.perform(post("/games/{gameId}/edit", 1).param("name", "")
							.with(csrf())
							.param("gametype", "Cards"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("game"))
				.andExpect(model().attributeHasFieldErrors("game", "name"))
				.andExpect(model().attributeHasFieldErrors("game", "maxPlayers"))
				.andExpect(view().name("games/updateGame"));
	}
	
}
