package org.springframework.samples.petclinic.web;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.service.SlotMachineService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(controllers= SlotMachineController.class,
includeFilters= {@ComponentScan.Filter(value = SlotgameFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = StatusFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = SlotMachineValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class SlotMachineControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SlotMachineService slotMachineService;
	
	private SlotMachine slotMachine;

	@BeforeEach
	void setup() {

		Slotgame slotgame = new Slotgame();
		slotgame.setId(1);
		slotgame.setName("Wandersong");
		slotgame.setJackpot(128);
		List<Slotgame> slotgames = new ArrayList<Slotgame>();
		slotgames.add(slotgame);
		given(this.slotMachineService.findSlotgames()).willReturn(slotgames);
		
		Status status1 = new Status();
		status1.setId(1);
		status1.setName("OK");
		Status status2 = new Status();
		status2.setId(1);
		status2.setName("COLLECT");	
		Status status3 = new Status();
		status3.setId(1);
		status3.setName("REPAIR");
		List<Status> statuses = new ArrayList<Status>();
		statuses.add(status1);
		statuses.add(status2);
		statuses.add(status3);
		given(this.slotMachineService.findStatus()).willReturn(statuses);
		
		SlotGain slotGain = new SlotGain();
		slotGain.setId(1);
		slotGain.setName("SlotGain1");
		slotGain.setAmount(200);
		slotGain.setDate(LocalDate.of(2020, 12, 27));
		slotGain.setSlotMachine(slotMachine);
		List<SlotGain> slotGains = new ArrayList<SlotGain>();
		slotGains.add(slotGain);
		given(this.slotMachineService.findGains()).willReturn(slotGains);
		
		SlotMachine slotMachine = new SlotMachine();
		slotMachine.setId(1);
		slotMachine.setSlotgame(slotgame);
		slotMachine.setStatus(status1);
		List<SlotMachine> slotMachines = new ArrayList<SlotMachine>();
		slotMachines.add(slotMachine);
		given(this.slotMachineService.findAll()).willReturn(slotMachines);
		given(this.slotMachineService.findSlotMachineById(1)).willReturn(Optional.of(slotMachine));

	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/slotmachines/new")).andExpect(status().isOk()).andExpect(model().attributeExists("slotMachine"))
		.andExpect(view().name("slotmachines/addSlotMachine"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/slotmachines/save").param("slotgame", "Wandersong")
						.with(csrf())
						.param("status", "OK"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("slotmachines/slotmachinesList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/slotmachines/save")
				.with(csrf())
				.param("status", "OK"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("slotMachine"))
			.andExpect(model().attributeHasFieldErrors("slotMachine", "slotgame"))
			.andExpect(view().name("slotmachines/addSlotMachine"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateSlotMachineForm() throws Exception {
		mockMvc.perform(get("/slotmachines/{slotMachineId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("slotMachine"))
				.andExpect(model().attribute("slotMachine", hasProperty("slotgame", is(slotMachineService.findSlotgames().toArray()[0]))))
				.andExpect(model().attribute("slotMachine", hasProperty("status", is(slotMachineService.findStatus().toArray()[0]))))
				.andExpect(view().name("slotmachines/updateSlotMachine"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateSlotMachineFormSuccess() throws Exception {
		mockMvc.perform(post("/slotmachines/{slotMachineId}/edit", 1).param("slotgame", "Wandersong")
				.with(csrf())
				.param("status", "COLLECT"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/slotmachines"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateSlotMachineFormHasErrors() throws Exception {
		mockMvc.perform(post("/slotmachines/{slotMachineId}/edit", 1).param("slotgame", "Wandersong")
					.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("slotMachine"))
				.andExpect(model().attributeHasFieldErrors("slotMachine", "status"))
				.andExpect(view().name("slotmachines/updateSlotMachine"));
	}

}
