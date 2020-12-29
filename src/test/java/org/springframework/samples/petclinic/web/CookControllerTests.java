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
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.service.CookService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=CookController.class,
includeFilters = @ComponentScan.Filter(value = CookValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CookControllerTests {

	private Cook cook;
	
	@MockBean
	private CookService cookService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.cook = new Cook();
		cook.setName("Emilio Tejero");
		cook.setDni("177013120H");
		cook.setPhone_number("999666333");
		
		given(this.cookService.findCookById(anyInt())).willReturn(Optional.of(cook));		
		given(this.cookService.findAll()).willReturn(Lists.list(cook));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/cooks/new")).andExpect(status().isOk()).andExpect(model().attributeExists("cook"))
		.andExpect(view().name("cooks/addCook"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/cooks/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("cooks/listCook"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/cooks/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("cook"))
			.andExpect(model().attributeHasFieldErrors("cook", "dni"))
			.andExpect(view().name("cooks/addCook"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/cooks/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("cook"))
			.andExpect(model().attributeHasFieldErrors("cook", "dni"))
			.andExpect(model().attributeHasFieldErrors("cook", "phone_number"))
			.andExpect(view().name("cooks/addCook"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateCookForm() throws Exception {
		mockMvc.perform(get("/cooks/{cookId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("cook"))
				.andExpect(model().attribute("cook", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("cook", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("cook", hasProperty("phone_number", is("999666333"))))
				.andExpect(view().name("cooks/updateCook"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCookFormSuccess() throws Exception {
    	mockMvc.perform(post("/cooks/{cookId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cooks"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCookFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/cooks/{cookId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("cook"))
				.andExpect(model().attributeHasFieldErrors("cook", "dni"))
				.andExpect(view().name("cooks/updateCook"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCookFormHasErrors() throws Exception {
    	mockMvc.perform(post("/cooks/{cookId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("cook"))
				.andExpect(model().attributeHasFieldErrors("cook", "dni"))
				.andExpect(model().attributeHasFieldErrors("cook", "phone_number"))
				.andExpect(view().name("cooks/updateCook"));
	}
}
