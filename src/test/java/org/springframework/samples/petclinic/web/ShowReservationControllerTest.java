package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ShowReservationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ShowReservationController.class,
includeFilters = { 	@ComponentScan.Filter(value = ShowReservationValidator.class, type = FilterType.ASSIGNABLE_TYPE),
					@ComponentScan.Filter(value = EventFormatter2.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ShowReservationControllerTest {

	private Event ev;
	private Client cl;
	private ShowReservation sr;
	private ShowReservation sr2;
	
	@MockBean
	private ShowReservationService showresServ;
	
	@MockBean
	private ClientService clientServ;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.ev = new Event();
		ev.setId(1);
		ev.setName("Magician Harambe");
		
		this.cl = new Client();
		cl.setId(1);
		cl.setDni("11111111A");
		cl.setName("Ray Bibbia");
		
		this.sr = new ShowReservation();
		sr.setId(1);
		sr.setSeats(5);
		sr.setClient(cl);
		sr.setEvent(ev);
		
		this.sr2 = new ShowReservation();
		sr2.setId(2);
		sr2.setSeats(4);
		sr2.setClient(cl);
		sr2.setEvent(ev);
		
		given(this.showresServ.findAvailableShows()).willReturn(Lists.list(ev));
		given(this.showresServ.findClientFromUsername(anyString())).willReturn(cl);
		given(this.showresServ.findAvailableSeats(anyInt())).willReturn(50);
		given(this.clientServ.findClientById(anyInt())).willReturn(Optional.of(cl));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/showress/new")).andExpect(status().isOk())
			.andExpect(model().attributeExists("showReservation"))
			.andExpect(view().name("showress/addShowReservation"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/showress/save")
						.with(csrf())
						.param("event", "Magician Harambe")
						.param("seats", "10"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("showress/myShowReservations"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/showress/save")
						.with(csrf())
						.param("seats", "-10"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("showReservation"))
			.andExpect(model().attributeHasFieldErrors("showReservation", "event"))
			.andExpect(model().attributeHasFieldErrors("showReservation", "seats"))
			.andExpect(view().name("showress/addShowReservation"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testNoAvailableSeats() throws Exception {
		given(this.showresServ.findAvailableSeats(anyInt())).willReturn(0);
		
		mockMvc.perform(post("/showress/save")
				.with(csrf())
				.param("event", "Magician Harambe")
				.param("seats", "10"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("showReservation"))
			.andExpect(model().attributeHasFieldErrors("showReservation", "seats"))
			.andExpect(view().name("showress/addShowReservation"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateShowReservationForm() throws Exception {
		given(this.showresServ.findShowReservationById(anyInt())).willReturn(Optional.of(sr));
		
		mockMvc.perform(get("/showress/{showresId}/edit/{clientId}", 1, 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("showReservation"))
				.andExpect(model().attribute("showReservation", hasProperty("seats", equalTo(5))))
				.andExpect(model().attribute("showReservation", hasProperty("event", equalTo(ev))))
				.andExpect(model().attribute("showReservation", hasProperty("client", equalTo(cl))))
				.andExpect(view().name("showress/updateShowReservation"));
	}
	
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateShowReservationFormSuccess() throws Exception {
    	mockMvc.perform(post("/showress/{showresId}/edit/{clientId}", 1, 1)
						.with(csrf())
						.param("seats", "7")
						.param("event", "Magician Harambe"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/showress/myShowReservations"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateShowReservationFormHasErrors() throws Exception {
    	mockMvc.perform(post("/showress/{showresId}/edit/{clientId}", 1, 1)
				.with(csrf())
				.param("seats", "-4444")
				.param("event", "Eddy Capoeira Showdown"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("showReservation"))
				.andExpect(model().attributeHasFieldErrors("showReservation", "event"))
				.andExpect(model().attributeHasFieldErrors("showReservation", "seats"))
				.andExpect(view().name("showress/updateShowReservation"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void userReservationsTest() throws Exception {
		given(this.showresServ.findReservationsForUser(any(Client.class))).willReturn(Lists.list(sr, sr2));
		mockMvc.perform(get("/showress/user"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("showress", Lists.list(sr, sr2)));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void userReservationsEmptySetTest() throws Exception {
		given(this.showresServ.findReservationsForUser(any(Client.class))).willReturn(Lists.emptyList());
		mockMvc.perform(get("/showress/user"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("showress", Lists.emptyList()));
	}
}
