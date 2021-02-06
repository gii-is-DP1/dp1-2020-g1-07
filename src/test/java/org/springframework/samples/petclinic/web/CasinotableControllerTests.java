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
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers= CasinotableController.class,
includeFilters= {@ComponentScan.Filter(value = GameTypeFormatter2.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = GameFormatter.class, type = FilterType.ASSIGNABLE_TYPE), 
				@ComponentScan.Filter(value = SkillFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = CasinotableValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CasinotableControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CasinotableService casinotableService;
	
	private Game game;
	private Game game2;
	private Game game3;
	private Game game4;
	private Game game5;
	
	private Casinotable casinotable;
	private Casinotable casinotable2;
	private Casinotable casinotable3;
	private Casinotable casinotable4;
	private Casinotable casinotable5;
	@BeforeEach
	void setup() {
		//Enumerates: Gametype | Skill
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
		given(this.casinotableService.findGameTypes()).willReturn(gametypes);
		
		Skill skill = new Skill();
		skill.setId(1);
		skill.setName("Amateur");
		Skill skill2 = new Skill();
		skill2.setId(2);
		skill2.setName("Proffesional");
		List<Skill> skills = new ArrayList<Skill>();
		skills.add(skill);
		skills.add(skill2);
		given(this.casinotableService.findSkills()).willReturn(skills);
		
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
		List<Game> cardsgames = new ArrayList<Game>();
		cardsgames.add(game);
		cardsgames.add(game2);
		cardsgames.add(game3);
		given(this.casinotableService.findGames()).willReturn(games);
		given(this.casinotableService.findGamesByGameType(2)).willReturn(cardsgames);
		
		casinotable = new Casinotable();
		casinotable.setName("Mesa 1");
		casinotable.setId(1);
		casinotable.setGame(game);
		casinotable.setGametype(gametype2);
		casinotable.setSkill(skill2);
		casinotable.setDate(LocalDate.of(2021, 1, 8));
		casinotable.setStartTime("13:00:00");
		casinotable.setEndingTime("14:00:00");
		
		casinotable2 = new Casinotable();
		casinotable2.setName("Mesa 2");
		casinotable2.setId(2);
		casinotable2.setGame(game2);
		casinotable2.setGametype(gametype2);
		casinotable2.setSkill(skill);
		casinotable2.setDate(LocalDate.of(2021, 1, 9));
		casinotable2.setStartTime("14:00:00");
		casinotable2.setEndingTime("16:00:00");
		
		casinotable3 = new Casinotable();
		casinotable3.setName("Mesa 3");
		casinotable3.setId(3);
		casinotable3.setGame(game3);
		casinotable3.setGametype(gametype2);
		casinotable3.setSkill(skill2);
		casinotable3.setDate(LocalDate.of(2021, 1, 7));
		casinotable3.setStartTime("17:00:00");
		casinotable3.setEndingTime("19:00:00");
		
		casinotable4 = new Casinotable();
		casinotable4.setName("Mesa 4");
		casinotable4.setId(4);
		casinotable4.setGame(game4);
		casinotable4.setGametype(gametype3);
		casinotable4.setSkill(skill);
		casinotable4.setDate(LocalDate.of(2021, 1, 6));
		casinotable4.setStartTime("18:00:00");
		casinotable4.setEndingTime("20:00:00");
		
		casinotable5 = new Casinotable();
		casinotable5.setName("Mesa 5");
		casinotable5.setId(5);
		casinotable5.setGame(game5);
		casinotable5.setGametype(gametype);
		casinotable5.setSkill(skill2);
		casinotable5.setDate(LocalDate.of(2021, 1, 4));
		casinotable5.setStartTime("13:00:00");
		casinotable5.setEndingTime("14:00:00");
		
		List<Casinotable> casinotables = new ArrayList<Casinotable>();
		casinotables.add(casinotable);
		casinotables.add(casinotable2);
		casinotables.add(casinotable3);
		casinotables.add(casinotable4);
		casinotables.add(casinotable5);
		given(this.casinotableService.findAll()).willReturn(casinotables);
		given(this.casinotableService.findCasinotableById(1)).willReturn(Optional.of(casinotable));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/casinotables/new")).andExpect(status().isOk()).andExpect(model().attributeExists("casinotable"))
		.andExpect(view().name("casinotables/addCasinotable"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/casinotables/save").param("date", "2020/12/21")
						.param("endingTime", "13:00:00")
						.param("startTime","11:00:00")
						.param("name", "Mesa 6")
						.param("game", "Poker")
						.with(csrf())
						.param("gametype", "Cards")
						.param("skill", "Amateur"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("casinotables/listCasinotable"));
	}
	
	
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/casinotables/save").param("date", "2020/12/21")
				.param("endingTime", "13:00:00")
				.param("startTime","11:00:00")
				.param("name", "Mesa 6")
				.param("game", "Poker")
				.with(csrf())
				.param("skill", "Amateur"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("casinotable"))
			.andExpect(model().attributeHasFieldErrors("casinotable", "gametype"))
			.andExpect(view().name("casinotables/addCasinotable"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateCasinotableForm() throws Exception {
		mockMvc.perform(get("/casinotables/{casinotableId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("casinotable"))
				.andExpect(model().attribute("casinotable", hasProperty("endingTime", is(casinotableService.findCasinotableById(1).get().getEndingTime()))))
				.andExpect(model().attribute("casinotable", hasProperty("startTime", is(casinotableService.findCasinotableById(1).get().getStartTime()))))
				.andExpect(model().attribute("casinotable", hasProperty("date", is(casinotableService.findCasinotableById(1).get().getDate()))))
				.andExpect(model().attribute("casinotable", hasProperty("name", is(casinotableService.findCasinotableById(1).get().getName()))))
				.andExpect(model().attribute("casinotable", hasProperty("game", is(casinotableService.findCasinotableById(1).get().getGame()))))
				.andExpect(model().attribute("casinotable", hasProperty("gametype", is(casinotableService.findGameTypes().toArray()[1]))))
				.andExpect(model().attribute("casinotable", hasProperty("skill", is(casinotableService.findSkills().toArray()[1]))))
				.andExpect(view().name("casinotables/updateCasinotable"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCasinotableFormSuccess() throws Exception {
		mockMvc.perform(post("/casinotables/{casinotableId}/edit", 1)
				.param("date", "2020/12/19")
				.param("endingTime", "13:00:00")
				.param("startTime","11:00:00")
				.param("name", "Mesa 6")
				.param("game", "Poker")
				.with(csrf())
				.param("gametype", "Cards")
				.param("skill", "Amateur"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/casinotables"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCasinotableFormHasErrors() throws Exception {
		mockMvc.perform(post("/casinotables/{casinotableId}/edit", 1)
				.param("date", "2020/12/21")
				.param("endingTime", "13:00:00")
				.param("startTime","11:00:00")
				.param("name", "Mesa 6")
				.with(csrf())
				.param("gametype", "Cards"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("casinotable"))
				.andExpect(model().attributeHasFieldErrors("casinotable", "game"))
				.andExpect(model().attributeHasFieldErrors("casinotable", "skill"))
				.andExpect(view().name("casinotables/updateCasinotable"));
	}
	
}
