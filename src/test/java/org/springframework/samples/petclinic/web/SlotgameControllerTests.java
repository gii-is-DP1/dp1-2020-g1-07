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
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.service.SlotMachineService;
import org.springframework.samples.petclinic.service.SlotgameService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
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
	
	@MockBean
	private SlotMachineService slotMachineService;
	
	private Slotgame slotgame;

	@BeforeEach
	void setup() {
		//Preparacion del slotgamre
		slotgame = new Slotgame();
		slotgame.setId(1);
		slotgame.setName("Wandersong");
		slotgame.setJackpot(128);
		given(this.slotgameService.findSlotgameById(1)).willReturn(Optional.of(slotgame));
		List<Slotgame> slotgames = new ArrayList<Slotgame>();
		slotgames.add(slotgame);
		given(this.slotgameService.findAll()).willReturn(slotgames);
		
		List<SlotMachine> slotmachines = new ArrayList<SlotMachine>();
		given(this.slotMachineService.findAll()).willReturn(slotmachines);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/slotgames/new")).andExpect(status().isOk()).andExpect(model().attributeExists("slotgame"))
		.andExpect(view().name("slotgames/addSlotgame"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/slotgames/save").param("name", "Cuphead")
						.with(csrf())
						.param("jackpot", "100"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("slotgames/slotgamesList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedName() throws Exception {
		mockMvc.perform(post("/slotgames/save").param("name", "Wandersong")
						.with(csrf())
						.param("jackpot", "100"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("slotgame"))
			.andExpect(model().attributeHasFieldErrors("slotgame", "name"))
			.andExpect(view().name("slotgames/addSlotgame"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/slotgames/save").param("name", "Cuphead")
						.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("slotgame"))
			.andExpect(model().attributeHasFieldErrors("slotgame", "jackpot"))
			.andExpect(view().name("slotgames/addSlotgame"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateslotgameForm() throws Exception {
		mockMvc.perform(get("/slotgames/{slotgameId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("slotgame"))
				.andExpect(model().attribute("slotgame", hasProperty("name", is("Wandersong"))))
				.andExpect(model().attribute("slotgame", hasProperty("jackpot", is(128))))
				.andExpect(view().name("slotgames/updateSlotgame"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateslotgameFormSuccess() throws Exception {
		mockMvc.perform(post("/slotgames/{slotgameId}/edit", 1)
							.with(csrf())
							.param("name", "Psychonauts")
							.param("jackpot", "48"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/slotgames"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateslotgameFormHasErrors() throws Exception {
		mockMvc.perform(post("/slotgames/{slotgameId}/edit", 1).param("name", "")
							.with(csrf())
							.param("jackpot", "48"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("slotgame"))
				.andExpect(model().attributeHasFieldErrors("slotgame", "name"))
				.andExpect(view().name("slotgames/updateSlotgame"));
	}
    
    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormRepeatedName() throws Exception {
		mockMvc.perform(post("/slotgames/{slotgameId}/edit", 2).param("name", "Wandersong")
						.with(csrf())
						.param("jackpot", "48"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("slotgame"))
			.andExpect(model().attributeHasFieldErrors("slotgame", "name"))
			.andExpect(view().name("slotgames/updateSlotgame"));
	}

}
