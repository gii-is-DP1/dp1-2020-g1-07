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
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.service.AuthorityService;
import org.springframework.samples.petclinic.service.ClientGainService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.ShowReservationService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= ClientController.class,
			includeFilters= @ComponentScan.Filter(value = ClientValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
			excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)

public class ClientControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientService clientService;
	
	@MockBean
	private ClientGainService cgainService;
	
	@MockBean
	private RestaurantReservationService restReserService;
	
	@MockBean
	private ShowReservationService showResService;
	
	@MockBean
	private UserService uservice;
	
	@MockBean
	private AuthorityService authoritiesService;
	
	private Client client;
	
	@BeforeEach
	void setup() {
		//Preparacion del cliente
		client = new Client();
		client.setId(1);
		client.setDni("45324586J");
		client.setName("Paco Perez");
		client.setPhone_number("643213480");
		given(this.clientService.findClientById(1)).willReturn(Optional.of(client));
		List<Client> clients = new ArrayList<Client>();
		clients.add(client);
		given(this.clientService.findAll()).willReturn(clients);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/clients/new")).andExpect(status().isOk()).andExpect(model().attributeExists("client"))
		.andExpect(view().name("clients/addClient"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/clients/save").param("name", "Paca Muñoz")
						.with(csrf())
						.param("phone_number", "643213430")
						.param("dni", "32324586J"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("clients/clientsList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedDni() throws Exception {
		mockMvc.perform(post("/clients/save").param("dni", "45324586J")
						.with(csrf())
						.param("name", "Juan Caro")
						.param("phone_number", "613223480"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("client"))
			.andExpect(model().attributeHasFieldErrors("client", "dni"))
			.andExpect(view().name("clients/addClient"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/clients/save").param("dni", "45324231J")
						.with(csrf())
						.param("name", "Joaquin Caro"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("client"))
			.andExpect(model().attributeHasFieldErrors("client", "phone_number"))
			.andExpect(view().name("clients/addClient"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateslotgameForm() throws Exception {
		mockMvc.perform(get("/clients/{clientId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("name", is("Paco Perez"))))
				.andExpect(model().attribute("client", hasProperty("phone_number", is("643213480"))))
				.andExpect(model().attribute("client", hasProperty("dni", is("45324586J"))))
				.andExpect(view().name("clients/updateClient"));
	}
	
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateslotgameFormSuccess() throws Exception {
		mockMvc.perform(post("/clients/{clientId}/edit", 1)
							.with(csrf())
							.param("name", "Juana Perez")
							.param("phone_number", "643213480")
							.param("dni", "45324586J"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/clients"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateslotgameFormHasErrors() throws Exception {
		mockMvc.perform(post("/clients/{clientId}/edit", 1).param("name", "")
							.with(csrf())
							.param("phone_number", "643213480")
							.param("dni", "45324586J"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "name"))
				.andExpect(view().name("clients/updateClient"));
	}
    
    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormRepeatedDni() throws Exception {
		mockMvc.perform(post("/clients/{clientId}/edit", 2).param("name", "Paca Caro")
						.with(csrf())
						.param("phone_number", "843453480")
						.param("dni", "45324586J"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("client"))
			.andExpect(model().attributeHasFieldErrors("client", "dni"))
			.andExpect(view().name("clients/updateClient"));
	}

}