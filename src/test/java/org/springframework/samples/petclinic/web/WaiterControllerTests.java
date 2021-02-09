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
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.RestaurantTableService;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.samples.petclinic.service.WaiterService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=WaiterController.class,
includeFilters = @ComponentScan.Filter(value = WaiterValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class WaiterControllerTests {

	private Waiter waiter;
	
	@MockBean
	private WaiterService waiterService;
	
	@MockBean
	private RestaurantTableService restaurantTableService;
	
	@MockBean
	private ScheduleService scheService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.waiter = new Waiter();
		waiter.setName("Emilio Tejero");
		waiter.setDni("177013120H");
		waiter.setPhone_number("999666333");
		
		given(this.waiterService.findWaiterById(anyInt())).willReturn(Optional.of(waiter));		
		given(this.waiterService.findAll()).willReturn(Lists.list(waiter));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/waiters/new")).andExpect(status().isOk()).andExpect(model().attributeExists("waiter"))
		.andExpect(view().name("waiters/addWaiter"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/waiters/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("waiters/listWaiter"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/waiters/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("waiter"))
			.andExpect(model().attributeHasFieldErrors("waiter", "dni"))
			.andExpect(view().name("waiters/addWaiter"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/waiters/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("waiter"))
			.andExpect(model().attributeHasFieldErrors("waiter", "dni"))
			.andExpect(model().attributeHasFieldErrors("waiter", "phone_number"))
			.andExpect(view().name("waiters/addWaiter"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateWaiterForm() throws Exception {
		mockMvc.perform(get("/waiters/{waiterId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("waiter"))
				.andExpect(model().attribute("waiter", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("waiter", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("waiter", hasProperty("phone_number", is("999666333"))))
				.andExpect(view().name("waiters/updateWaiter"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateWaiterFormSuccess() throws Exception {
    	mockMvc.perform(post("/waiters/{waiterId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/waiters"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateWaiterFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/waiters/{waiterId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("waiter"))
				.andExpect(model().attributeHasFieldErrors("waiter", "dni"))
				.andExpect(view().name("waiters/updateWaiter"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateWaiterFormHasErrors() throws Exception {
    	mockMvc.perform(post("/waiters/{waiterId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("waiter"))
				.andExpect(model().attributeHasFieldErrors("waiter", "dni"))
				.andExpect(model().attributeHasFieldErrors("waiter", "phone_number"))
				.andExpect(view().name("waiters/updateWaiter"));
	}
}
