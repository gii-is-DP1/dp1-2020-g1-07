package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
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

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.samples.petclinic.service.CroupierService;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=CroupierController.class,
includeFilters = {@ComponentScan.Filter(value = CroupierValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
				 @ComponentScan.Filter(value = CasinotableFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
				 @ComponentScan.Filter(value = CasinotableValidator.class, type = FilterType.ASSIGNABLE_TYPE), }, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CroupierControllerTests {

	private Croupier croup;
	
	@MockBean
	private CroupierService croupierService;
	
	@MockBean
	private ScheduleService scheService;
	
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
		this.croup = new Croupier();
		croup.setName("Emilio Tejero");
		croup.setDni("177013120H");
		croup.setPhone_number("999666333");
		
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
		
		Skill skill = new Skill();
		skill.setId(1);
		skill.setName("Amateur");
		Skill skill2 = new Skill();
		skill2.setId(2);
		skill2.setName("Proffesional");
		List<Skill> skills = new ArrayList<Skill>();
		skills.add(skill);
		skills.add(skill2);
		
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
		this.croup = new Croupier();
		croup.setName("Emilio Tejero");
		croup.setDni("177013120H");
		croup.setPhone_number("999666333");
		croup.setCasinotable(casinotable);
		given(this.croupierService.findCasinotables()).willReturn(casinotables);
		given(this.casinotableService.findCasinotableById(1)).willReturn(Optional.of(casinotable));
		
		given(this.croupierService.findCroupierById(anyInt())).willReturn(Optional.of(croup));		
		given(this.croupierService.findAll()).willReturn(Lists.list(croup));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/croupiers/new")).andExpect(status().isOk()).andExpect(model().attributeExists("croupier"))
		.andExpect(view().name("croupiers/addCroupier"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/croupiers/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122")
						.param("casinotable", "Mesa 1"))		
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("croupiers/listCroupier"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/croupiers/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122")
					.param("casinotable", "Mesa 1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("croupier"))
			.andExpect(model().attributeHasFieldErrors("croupier", "dni"))
			.andExpect(view().name("croupiers/addCroupier"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/croupiers/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22")
		.param("casinotable", "Mesa 1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("croupier"))
			.andExpect(model().attributeHasFieldErrors("croupier", "dni"))
			.andExpect(model().attributeHasFieldErrors("croupier", "phone_number"))
			.andExpect(view().name("croupiers/addCroupier"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateCroupierForm() throws Exception {
		mockMvc.perform(get("/croupiers/{croupierId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("croupier"))
				.andExpect(model().attribute("croupier", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("croupier", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("croupier", hasProperty("phone_number", is("999666333"))))
				.andExpect(model().attribute("croupier", hasProperty("casinotable", is(croupierService.findCasinotables().toArray()[0]))))
				.andExpect(view().name("croupiers/updateCroupier"));
	}
	
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCroupierFormSuccess() throws Exception {
    	mockMvc.perform(post("/croupiers/{croupierId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122")
						.param("casinotable", "Mesa 1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/croupiers"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCroupierFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/croupiers/{croupierId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122")
				.param("casinotable", "Mesa 1"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("croupier"))
				.andExpect(model().attributeHasFieldErrors("croupier", "dni"))
				.andExpect(view().name("croupiers/updateCroupier"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCroupierFormHasErrors() throws Exception {
    	mockMvc.perform(post("/croupiers/{croupierId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22")
				.param("casinotable", "Mesa 1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("croupier"))
				.andExpect(model().attributeHasFieldErrors("croupier", "dni"))
				.andExpect(model().attributeHasFieldErrors("croupier", "phone_number"))
				.andExpect(view().name("croupiers/updateCroupier"));
	}
}
