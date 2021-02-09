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
import org.springframework.samples.petclinic.model.MaintenanceWorker;
import org.springframework.samples.petclinic.service.MaintenanceWorkerService;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=MaintenanceWorkerController.class,
includeFilters = @ComponentScan.Filter(value = MaintenanceWorkerValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MaintenanceWorkerControllerTests {

	private MaintenanceWorker mworker;
	
	@MockBean
	private MaintenanceWorkerService maintenanceWorkerService;
	
	@MockBean
	private ScheduleService scheService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.mworker = new MaintenanceWorker();
		mworker.setName("Emilio Tejero");
		mworker.setDni("177013120H");
		mworker.setPhone_number("999666333");
		
		given(this.maintenanceWorkerService.findMaintenanceWorkerById(anyInt())).willReturn(Optional.of(mworker));		
		given(this.maintenanceWorkerService.findAll()).willReturn(Lists.list(mworker));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/maintenanceWorkers/new")).andExpect(status().isOk()).andExpect(model().attributeExists("maintenanceWorker"))
		.andExpect(view().name("maintenanceWorkers/addMaintenanceWorker"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/maintenanceWorkers/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("maintenanceWorkers/listMaintenanceWorker"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/maintenanceWorkers/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("maintenanceWorker"))
			.andExpect(model().attributeHasFieldErrors("maintenanceWorker", "dni"))
			.andExpect(view().name("maintenanceWorkers/addMaintenanceWorker"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/maintenanceWorkers/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("maintenanceWorker"))
			.andExpect(model().attributeHasFieldErrors("maintenanceWorker", "dni"))
			.andExpect(model().attributeHasFieldErrors("maintenanceWorker", "phone_number"))
			.andExpect(view().name("maintenanceWorkers/addMaintenanceWorker"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateMaintenanceWorkerForm() throws Exception {
		mockMvc.perform(get("/maintenanceWorkers/{maintenanceWorkerId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("maintenanceWorker"))
				.andExpect(model().attribute("maintenanceWorker", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("maintenanceWorker", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("maintenanceWorker", hasProperty("phone_number", is("999666333"))))
				.andExpect(view().name("maintenanceWorkers/updateMaintenanceWorker"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateMaintenanceWorkerFormSuccess() throws Exception {
    	mockMvc.perform(post("/maintenanceWorkers/{maintenanceWorkerId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/maintenanceWorkers"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateMaintenanceWorkerFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/maintenanceWorkers/{maintenanceWorkerId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("maintenanceWorker"))
				.andExpect(model().attributeHasFieldErrors("maintenanceWorker", "dni"))
				.andExpect(view().name("maintenanceWorkers/updateMaintenanceWorker"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateMaintenanceWorkerFormHasErrors() throws Exception {
    	mockMvc.perform(post("/maintenanceWorkers/{maintenanceWorkerId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("maintenanceWorker"))
				.andExpect(model().attributeHasFieldErrors("maintenanceWorker", "dni"))
				.andExpect(model().attributeHasFieldErrors("maintenanceWorker", "phone_number"))
				.andExpect(view().name("maintenanceWorkers/updateMaintenanceWorker"));
	}
}
