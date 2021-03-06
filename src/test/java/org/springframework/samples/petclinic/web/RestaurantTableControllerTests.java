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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	private RestaurantTableService restaurantTableService;
	
	@MockBean
	private RestaurantReservationService restaurantReservationService;

	@MockBean
	private ScheduleService scheduleservice;
	
	private RestaurantTable restaurantTable;
	private Waiter waiter;

	@BeforeEach
	void setup() {
		restaurantTable = new RestaurantTable();
		restaurantTable.setId(1);
		this.restaurantTable.setSize(3);
		
		this.waiter = new Waiter();
		waiter.setId(1);
		waiter.setName("Emilio Tejero");
		waiter.setDni("177013120H");
		waiter.setPhone_number("999666333");
		
        Set<Waiter> waiters = new HashSet<Waiter>();
        waiters.add(waiter);
		restaurantTable.setWaiters(waiters);
				
		Collection<Employee> waiters_coll = new ArrayList<Employee>();
	    given(this.scheduleservice.findEmployees()).willReturn(waiters_coll);
	    
	    Collection<RestaurantReservation> restaurantreservations = new ArrayList<RestaurantReservation>();
	    given(this.restaurantReservationService.findAll()).willReturn(restaurantreservations);
				
		List<RestaurantTable> restauranttables = new ArrayList<RestaurantTable>();
		restauranttables.add(restaurantTable);
	    given(this.restaurantTableService.findAll()).willReturn(restauranttables);
		given(this.restaurantTableService.findRestaurantTableId(1)).willReturn(Optional.of(restaurantTable));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/restaurantTables/new")).andExpect(status().isOk()).andExpect(model().attributeExists("restaurantTable"))
		.andExpect(view().name("restaurantTables/addRestaurantTable"));
	}
	
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantTables/save").param("size", "3").with(csrf())
						.param("waiter", "177013120H"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("restaurantTables/restaurantTablesList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantTables/save").param("size", "0")
						.with(csrf())
						.param("waiter", "177013120H"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("restaurantTable"))
			.andExpect(model().attributeHasFieldErrors("restaurantTable", "size"))
			.andExpect(view().name("restaurantTables/addRestaurantTable"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateRestaurantTableForm() throws Exception {
		mockMvc.perform(get("/restaurantTables/{restaurantTableId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("restaurantTable"))
				.andExpect(model().attribute("restaurantTable", hasProperty("waiters", is(restaurantTableService.findRestaurantTableId(1).get().getWaiters()))))
				.andExpect(model().attribute("restaurantTable", hasProperty("size", is(restaurantTableService.findRestaurantTableId(1).get().getSize()))))
				.andExpect(view().name("restauranttables/updateRestaurantTable"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateRestaurantTableFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantTables/{restaurantTableId}/edit", 1)
				.param("waiter", "177013120H")
				.param("size", "3")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/restaurantTables"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateRestaurantTableFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantTables/{restaurantTableId}/edit", 1)
				.param("waiter", "177013120H")
				.param("size", "0")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("restaurantTable"))
				.andExpect(model().attributeHasFieldErrors("restaurantTable", "size"))
				.andExpect(view().name("restaurantTables/updateRestaurantTable"));
	}
}
