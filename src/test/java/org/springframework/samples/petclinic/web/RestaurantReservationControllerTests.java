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
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.RestaurantTableService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= RestaurantReservationController.class,
includeFilters= {@ComponentScan.Filter(value = RestaurantTableFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = ClientFormatter2.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = TimeIntervalFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = RestaurantReservationValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class RestaurantReservationControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RestaurantReservationService reservationService;
	
	@MockBean
	private RestaurantTableService tableService;
	
	private RestaurantReservation reservation;
	
	@BeforeEach
	void setup() {

		//Preparamos una mesa
		RestaurantTable restauranttable = new RestaurantTable();
		restauranttable.setId(1);
		restauranttable.setSize(8);
		restauranttable.setWaiter(new Waiter());
		List<RestaurantTable> restaurantTables = new ArrayList<RestaurantTable>();
		restaurantTables.add(restauranttable);
		given(this.reservationService.findRestaurantTables()).willReturn(restaurantTables);
		given(this.tableService.findAll()).willReturn(restaurantTables);
		
		//Preparamos un cliente
		Client client = new Client();
		client.setDni("11122233A");
		client.setId(1);
		client.setName("Jacobo García");
		client.setPhone_number("123456789");
		User user = new User();
		user.setUsername("spring");
		user.setDni("11122233A");
		user.setEnabled(true);
		user.setPassword("spring");
		Authorities auth = new Authorities();
		auth.setAuthority("client");
		auth.setId(1);
		auth.setUser(user);
		client.setUser(user);
		List<Client> clients = new ArrayList<Client>();
		clients.add(client);
		given(this.reservationService.findClients()).willReturn(clients);
		given(this.reservationService.findClientFromUsername("spring")).willReturn(client);
		given(this.reservationService.getAuthority("spring")).willReturn(auth);
		
		//Preparamos un intervalo de tiempo
		TimeInterval timeInterval = new TimeInterval();
		timeInterval.setId(1);
		timeInterval.setName("13:00-14:00");
		List<TimeInterval> timeIntervals = new ArrayList<TimeInterval>();
		timeIntervals.add(timeInterval);
		given(this.reservationService.findTimeIntervals()).willReturn(timeIntervals);
		
		//Preparamos una fecha
		LocalDate date = LocalDate.of(2021, 02, 15);
		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates.add(date);
		given(this.reservationService.findAllDates()).willReturn(dates);
		
		reservation = new RestaurantReservation();
		reservation.setId(1);
		reservation.setDate(date);
		reservation.setClient(client);
		reservation.setRestauranttable(restauranttable);
		reservation.setTimeInterval(timeInterval);
		
		List<RestaurantReservation> reservations = new ArrayList<RestaurantReservation>();
		reservations.add(reservation);
		given(this.reservationService.findAll()).willReturn(reservations);
		given(this.reservationService.findRestaurantReservationId(1)).willReturn(Optional.of(reservation));
		given(this.reservationService.findRestaurantReservationsByDate(date)).willReturn(reservations);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/restaurantreservations/new")).andExpect(status().isOk()).andExpect(model().attributeExists("restaurantReservation"))
		.andExpect(view().name("restaurantreservations/addRestaurantReservation"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantreservations/save").param("client", "11122233A")
						.with(csrf())
						.param("timeInterval", "1")
						.param("restauranttable", "1")
						.param("date", "2021-02-16"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("restaurantreservations/restaurantreservationsList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantreservations/save").param("client", "11122233A")
				.with(csrf())
				.param("timeInterval", "1")
				.param("restauranttable", "1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("restaurantReservation"))
			.andExpect(model().attributeHasFieldErrors("restaurantReservation", "date"))
			.andExpect(view().name("restaurantreservations/addRestaurantReservation"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/restaurantreservations/{restaurantReservationId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("restaurantReservation"))
				.andExpect(model().attribute("restaurantReservation", hasProperty("date", is(LocalDate.of(2021, 02, 15)))))
				.andExpect(model().attribute("restaurantReservation", hasProperty("client", is(reservationService.findClients().toArray()[0]))))
				.andExpect(model().attribute("restaurantReservation", hasProperty("timeInterval", is(reservationService.findTimeIntervals().toArray()[0]))))
				.andExpect(model().attribute("restaurantReservation", hasProperty("restauranttable", is(reservationService.findRestaurantTables().toArray()[0]))))
				.andExpect(view().name("restaurantreservations/updateRestaurantReservation"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/restaurantreservations/{restaurantReservationId}/edit", 1)
					.param("client", "11122233A")
					.with(csrf())
					.param("timeInterval", "1")
					.param("restauranttable", "1")
					.param("date", "2021-05-20"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/restaurantreservations"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/restaurantreservations/{restaurantReservationId}/edit", 1).param("name", "")
					.param("client", "11122233A")
					.with(csrf())
					.param("restauranttable", "1")
					.param("date", "2021-05-20"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("restaurantReservation"))
				.andExpect(model().attributeHasFieldErrors("restaurantReservation", "timeInterval"))
				.andExpect(view().name("restaurantreservations/updateRestaurantReservation"));
	}


}
