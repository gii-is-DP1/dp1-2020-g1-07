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
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.service.SlotGainService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= SlotGainController.class,
			includeFilters= {@ComponentScan.Filter(value = SlotMachineFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
							@ComponentScan.Filter(value = SlotGainValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
			excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
public class SlotGainControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SlotGainService slotGainService;
	
	private SlotGain slotGain;

	@BeforeEach
	void setup() {

		Slotgame slotgame = new Slotgame();
		slotgame.setId(1);
		slotgame.setName("Wandersong");
		slotgame.setJackpot(128);
		
		Status status = new Status();
		status.setId(1);
		status.setName("OK");	
		
		SlotMachine slotMachine = new SlotMachine();
		slotMachine.setId(1);
		slotMachine.setSlotgame(slotgame);
		slotMachine.setStatus(status);
		List<SlotMachine> slotMachines = new ArrayList<SlotMachine>();
		slotMachines.add(slotMachine);
		given(this.slotGainService.findSlotMachines()).willReturn(slotMachines);
		
		slotGain = new SlotGain();
		slotGain.setId(1);
		slotGain.setName("SlotGain1");
		slotGain.setAmount(200);
		slotGain.setDate(LocalDate.of(2020, 12, 27));
		slotGain.setSlotMachine(slotMachine);
		given(this.slotGainService.findSlotGainById(1)).willReturn(Optional.of(slotGain));
		List<SlotGain> slotgains = new ArrayList<SlotGain>();
		slotgains.add(slotGain);
		given(this.slotGainService.findAll()).willReturn(slotgains);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/slotgains/new")).andExpect(status().isOk()).andExpect(model().attributeExists("slotGain"))
		.andExpect(view().name("slotgains/addSlotGain"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/slotgains/save").param("date", "2020/12/10")
						.with(csrf())
						.param("amount", "100")
						.param("slotMachine", "1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("slotgains/slotGainsList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedDateAndSlot() throws Exception {
		mockMvc.perform(post("/slotgains/save").param("date", "2020/12/27")
				.with(csrf())
				.param("amount", "100")
				.param("slotMachine", "1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("slotGain"))
			.andExpect(model().attributeHasFieldErrors("slotGain", "date"))
			.andExpect(view().name("slotgains/addSlotGain"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/slotgains/save").param("date", "2020/12/27")
				.with(csrf())
				.param("slotMachine", "1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("slotGain"))
			.andExpect(model().attributeHasFieldErrors("slotGain", "amount"))
			.andExpect(view().name("slotgains/addSlotGain"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateSlotGainForm() throws Exception {
		mockMvc.perform(get("/slotgains/{slotGainId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("slotGain"))
				.andExpect(model().attribute("slotGain", hasProperty("date", is(LocalDate.of(2020, 12, 27)))))
				.andExpect(model().attribute("slotGain", hasProperty("amount", is(200))))
				.andExpect(model().attribute("slotGain", hasProperty("slotMachine", is(slotGainService.findSlotMachines().toArray()[0]))))
				.andExpect(view().name("slotgains/updateSlotGain"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateSlotGainFormSuccess() throws Exception {
		mockMvc.perform(post("/slotgains/{slotGainId}/edit", 1)
							.with(csrf())
							.param("date", "2020/12/20")
							.param("amount", "100")
							.param("slotMachine", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/slotgains"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateSlotGainFormHasErrors() throws Exception {
		mockMvc.perform(post("/slotgains/{slotGainId}/edit", 1)
							.with(csrf())
							.param("date", "2020/12/20")
							.param("amount", "-100")
							.param("slotMachine", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("slotGain"))
				.andExpect(model().attributeHasFieldErrors("slotGain", "amount"))
				.andExpect(view().name("slotgains/updateSlotGain"));
	}
    
    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormRepeatedDateAndSlot() throws Exception {
		mockMvc.perform(post("/slotgains/{slotGainId}/edit", 2).param("date", "2020/12/27")
				.with(csrf())
				.param("amount", "100")
				.param("slotMachine", "1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("slotGain"))
			.andExpect(model().attributeHasFieldErrors("slotGain", "date"))
			.andExpect(view().name("slotgains/updateSlotGain"));
	}

}
