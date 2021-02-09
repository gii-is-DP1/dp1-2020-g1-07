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
import org.springframework.samples.petclinic.model.Chef;
import org.springframework.samples.petclinic.service.ChefService;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ChefController.class,
includeFilters = @ComponentScan.Filter(value = ChefValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ChefControllerTests {

	private Chef chef;
	
	@MockBean
	private ChefService chefService;
	
	@MockBean
	private ScheduleService scheService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.chef = new Chef();
		chef.setName("Emilio Tejero");
		chef.setDni("177013120H");
		chef.setPhone_number("999666333");
		
		given(this.chefService.findChefById(anyInt())).willReturn(Optional.of(chef));		
		given(this.chefService.findAll()).willReturn(Lists.list(chef));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/chefs/new")).andExpect(status().isOk()).andExpect(model().attributeExists("chef"))
		.andExpect(view().name("chefs/addChef"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/chefs/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("chefs/listChef"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/chefs/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("chef"))
			.andExpect(model().attributeHasFieldErrors("chef", "dni"))
			.andExpect(view().name("chefs/addChef"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/chefs/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("chef"))
			.andExpect(model().attributeHasFieldErrors("chef", "dni"))
			.andExpect(model().attributeHasFieldErrors("chef", "phone_number"))
			.andExpect(view().name("chefs/addChef"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateChefForm() throws Exception {
		mockMvc.perform(get("/chefs/{chefId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("chef"))
				.andExpect(model().attribute("chef", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("chef", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("chef", hasProperty("phone_number", is("999666333"))))
				.andExpect(view().name("chefs/updateChef"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateChefFormSuccess() throws Exception {
    	mockMvc.perform(post("/chefs/{chefId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/chefs"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateChefFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/chefs/{chefId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("chef"))
				.andExpect(model().attributeHasFieldErrors("chef", "dni"))
				.andExpect(view().name("chefs/updateChef"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateChefFormHasErrors() throws Exception {
    	mockMvc.perform(post("/chefs/{chefId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("chef"))
				.andExpect(model().attributeHasFieldErrors("chef", "dni"))
				.andExpect(model().attributeHasFieldErrors("chef", "phone_number"))
				.andExpect(view().name("chefs/updateChef"));
	}
}
