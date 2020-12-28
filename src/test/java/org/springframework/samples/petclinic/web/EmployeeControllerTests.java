package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
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
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=EmployeeController.class,
includeFilters = @ComponentScan.Filter(value = ClientValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class EmployeeControllerTests {

	private static final String TEST_DATE = "2020-09-07";

	private Employee emp;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.emp = new Employee();
		emp.setName("Emilio Tejero");
		emp.setDni("177013120H");
		emp.setPhone_number("999666333");
		
		given(this.employeeService.findEmployeeById(anyInt())).willReturn(Optional.of(emp));		
		given(this.employeeService.findAll()).willReturn(Lists.list(emp));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/employees/new")).andExpect(status().isOk()).andExpect(model().attributeExists("employee"))
		.andExpect(view().name("employees/addEmployee"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/employees/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("employees/listEmployee"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/employees/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("clientGain"))
			.andExpect(model().attributeHasFieldErrors("clientGain", "dni"))
			.andExpect(view().name("cgains/addEmployee"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/employees/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("clientGain"))
			.andExpect(model().attributeHasFieldErrors("clientGain", "dni"))
			.andExpect(model().attributeHasFieldErrors("clientGain", "phone_number"))
			.andExpect(view().name("employees/addEmployee"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateEmployeeForm() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("employee"))
				.andExpect(model().attribute("employee", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("employee", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("employee", hasProperty("phone_number", is("999666333"))))
				.andExpect(view().name("employees/updateEmployee"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateEmployeeFormSuccess() throws Exception {
    	mockMvc.perform(post("/employees/{employeeId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/employees"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateEmployeeFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/employees/{employeeId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("clientGain"))
				.andExpect(model().attributeHasFieldErrors("clientGain", "dni"))
				.andExpect(view().name("employees/updateEmployee"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateEmployeeFormHasErrors() throws Exception {
    	mockMvc.perform(post("/employees/{employeeId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("clientGain"))
				.andExpect(model().attributeHasFieldErrors("clientGain", "dni"))
				.andExpect(model().attributeHasFieldErrors("clientGain", "phone_number"))
				.andExpect(view().name("employees/updateEmployee"));
	}
}
