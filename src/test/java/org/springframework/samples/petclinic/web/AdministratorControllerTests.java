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
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.service.AdministratorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AdministratorController.class,
includeFilters = @ComponentScan.Filter(value = AdministratorValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AdministratorControllerTests {

	private Administrator admin;
	
	@MockBean
	private AdministratorService administratorService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.admin = new Administrator();
		admin.setName("Emilio Tejero");
		admin.setDni("177013120H");
		admin.setPhone_number("999666333");
		
		given(this.administratorService.findAdministratorById(anyInt())).willReturn(Optional.of(admin));		
		given(this.administratorService.findAll()).willReturn(Lists.list(admin));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/administrators/new")).andExpect(status().isOk()).andExpect(model().attributeExists("administrator"))
		.andExpect(view().name("administrators/addAdministrator"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/administrators/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("administrators/listAdministrator"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/administrators/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("administrator"))
			.andExpect(model().attributeHasFieldErrors("administrator", "dni"))
			.andExpect(view().name("administrators/addAdministrator"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/administrators/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("administrator"))
			.andExpect(model().attributeHasFieldErrors("administrator", "dni"))
			.andExpect(model().attributeHasFieldErrors("administrator", "phone_number"))
			.andExpect(view().name("administrators/addAdministrator"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateAdministratorForm() throws Exception {
		mockMvc.perform(get("/administrators/{administratorId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("administrator"))
				.andExpect(model().attribute("administrator", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("administrator", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("administrator", hasProperty("phone_number", is("999666333"))))
				.andExpect(view().name("administrators/updateAdministrator"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAdministratorFormSuccess() throws Exception {
    	mockMvc.perform(post("/administrators/{administratorId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/administrators"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAdministratorFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/administrators/{administratorId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("administrator"))
				.andExpect(model().attributeHasFieldErrors("administrator", "dni"))
				.andExpect(view().name("administrators/updateAdministrator"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAdministratorFormHasErrors() throws Exception {
    	mockMvc.perform(post("/administrators/{administratorId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("administrator"))
				.andExpect(model().attributeHasFieldErrors("administrator", "dni"))
				.andExpect(model().attributeHasFieldErrors("administrator", "phone_number"))
				.andExpect(view().name("administrators/updateAdministrator"));
	}
}
