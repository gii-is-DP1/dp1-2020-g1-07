package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.RestaurantTableService;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= RestaurantTableController.class,
includeFilters= {@ComponentScan.Filter(value = EmployeeFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = RestaurantTableValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class RestaurantTableControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RestaurantTableService restauranttableService;
	
	@MockBean
	private RestaurantReservationService restaurantReservationService;

	@MockBean
	private ScheduleService scheduleservice;
	
	private RestaurantTable restauranttable;
	private Waiter waiter;

	@BeforeEach
	void setup() {
		restauranttable = new RestaurantTable();
		restauranttable.setId(1);
		this.restauranttable.setSize(3);
		this.waiter = new Waiter();
		waiter.setId(1);
		waiter.setName("Emilio Tejero");
		waiter.setDni("177013120H");
		waiter.setPhone_number("999666333");
		this.restauranttable.setWaiter(waiter);
		
		Collection<Employee> waiters = new ArrayList<Employee>();
		waiters.add(waiter);
	    given(this.scheduleservice.findEmployees()).willReturn(waiters);
	    
	    Collection<RestaurantReservation> restaurantreservations = new ArrayList<RestaurantReservation>();
	    given(this.restaurantReservationService.findAll()).willReturn(restaurantreservations);
				
		List<RestaurantTable> restauranttables = new ArrayList<RestaurantTable>();
		restauranttables.add(restauranttable);
	    given(this.restauranttableService.findAll()).willReturn(restauranttables);
		given(this.restauranttableService.findRestaurantTableId(1)).willReturn(Optional.of(restauranttable));
	}

	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/restauranttables/new")).andExpect(status().isOk()).andExpect(model().attributeExists("restaurantTable"))
		.andExpect(view().name("restauranttables/addRestaurantTable"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/restauranttables/save").param("size", "3").with(csrf())
						.param("waiter", "177013120H"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("restauranttables/restauranttablesList"));
	}
	/*
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/restauranttables/save").param("size", "3")
						.with(csrf())
						.param("waiter", "177013120B"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("restauranttables"))
			.andExpect(model().attributeHasFieldErrors("restauranttables", "waiter"))
			.andExpect(view().name("restauranttables/addRestaurantTable"));
	}
	*/
	
}
