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
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.samples.petclinic.service.CroupierService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=CroupierController.class,
includeFilters = @ComponentScan.Filter(value = CroupierValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CroupierControllerTests {

	private Croupier croup;
	
	@MockBean
	private CroupierService croupierService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.croup = new Croupier();
		croup.setName("Emilio Tejero");
		croup.setDni("177013120H");
		croup.setPhone_number("999666333");
		
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
						.param("phone_number", "957202122"))
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
					.param("phone_number", "957202122"))
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
						.param("phone_number", "957 - 20 21 22"))
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
				.andExpect(view().name("croupiers/updateCroupier"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCroupierFormSuccess() throws Exception {
    	mockMvc.perform(post("/croupiers/{croupierId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
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
				.param("phone_number", "957202122"))
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
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("croupier"))
				.andExpect(model().attributeHasFieldErrors("croupier", "dni"))
				.andExpect(model().attributeHasFieldErrors("croupier", "phone_number"))
				.andExpect(view().name("croupiers/updateCroupier"));
	}
}
