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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.ShowReservationService;
import org.springframework.samples.petclinic.service.StageService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= EventController.class,
includeFilters= {@ComponentScan.Filter(value = ShowTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = ArtistFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = EventValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class EventControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EventService eventService;
	
	@MockBean
	private ShowReservationService srService;
	
	@MockBean
	private StageService stageService;
	
	private Event event;
	private Event event2;

	@BeforeEach
	void setup(){
		//Enumerate: ShowType
		ShowType showtype = new ShowType();
		showtype.setId(1);
		showtype.setName("Music");
		
		ShowType showtype2 = new ShowType();
		showtype2.setId(2);
		showtype2.setName("Theather");

		ShowType showtype3 = new ShowType();
		showtype3.setId(3);
		showtype3.setName("Magic");
		List<ShowType> showtypes = new ArrayList<ShowType>();
		showtypes.add(showtype);showtypes.add(showtype2);showtypes.add(showtype3);
		given(this.eventService.findShowTypes()).willReturn(showtypes);
		
		Artist employee = new Artist();
		employee.setId(1);
		employee.setDni("12345678a");
		employee.setName("Manuel Rodríguez");
		employee.setPhone_number("617319332");
		
		Artist employee2 = new Artist();
		employee2.setId(2);
		employee2.setDni("45345678a");
		employee2.setName("Luisa Caro");
		employee2.setPhone_number("613413232");
		
		Artist employee3 = new Artist();
		employee3.setId(3);
		employee3.setDni("78345678a");
		employee3.setName("Juan Alvarez");
		employee3.setPhone_number("617789332");
		List<Artist> artistsList = new ArrayList<Artist>();
		artistsList.add(employee);artistsList.add(employee2);artistsList.add(employee3);
		given(this.eventService.findArtists()).willReturn(artistsList);

		event = new Event();
		Set<Artist> artists = new HashSet<Artist>();
		artists.add(employee);
		event.setArtists(artists);
		event.setDate(LocalDate.of(2020, 12, 31));
		event.setId(1);
		event.setName("Magic and Pasion");
		event.setShowtype_id(showtype3);
		
		event2 = new Event();
		artists = new HashSet<Artist>();
		artists.add(employee2);
		artists.add(employee3);
		event.setArtists(artists);
		event2.setDate(LocalDate.of(2019, 11, 23));
		event2.setId(2);
		event2.setName("Hamlet");
		event2.setShowtype_id(showtype2);
		
		
		Stage stage = new Stage();
		stage.setId(1);
		stage.setCapacity(100);
		
		Stage stage2= new Stage();
		stage2.setId(2);
		stage2.setCapacity(130);
		
		List<Stage> stages = new ArrayList<Stage>();
		stages.add(stage);stages.add(stage2);
		given(this.eventService.findStages()).willReturn(stages);
	
		event.setStage_id(stage);event2.setStage_id(stage2);
		List<Event> events = new ArrayList<Event>();
		events.add(event);events.add(event2);
		given(this.eventService.findAll()).willReturn(events);
		given(this.eventService.findEventbyId(1)).willReturn(Optional.of(event));
		given(this.srService.findAll()).willReturn(Lists.emptyList());
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/events/new")).andExpect(status().isOk()).andExpect(model().attributeExists("event"))
		.andExpect(view().name("events/addEvent"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/events/save").param("name", "Magic and Ilusion")
						.with(csrf())
						.param("date", "2020/12/25")
						.param("showtype_id", "Magic")
						.param("stage_id", "1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("events/listEvent"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/events/save").param("name", "Magic and Ilusion")
						.with(csrf())
						.param("showtype_id", "Magic")
						.param("stage_id", "1"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeHasErrors("event"))
		.andExpect(model().attributeHasFieldErrors("event", "date"))
		.andExpect(view().name("events/addEvent"));
	}
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedName() throws Exception {
		mockMvc.perform(post("/events/save").param("name", "Magic and Pasion")
						.with(csrf())
						.param("date", "2020/12/25")
						.param("showtype_id", "Magic")
						.param("stage_id", "1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("event"))
			.andExpect(model().attributeHasFieldErrors("event", "name"))
			.andExpect(view().name("events/addEvent"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateEventForm() throws Exception {
		mockMvc.perform(get("/events/{eventId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("event"))
				.andExpect(model().attribute("event", hasProperty("name", is(eventService.findEventbyId(1).get().getName()))))
				.andExpect(model().attribute("event", hasProperty("date", is(eventService.findEventbyId(1).get().getDate()))))
				.andExpect(model().attribute("event", hasProperty("showtype_id", is(eventService.findShowTypes().toArray()[2]))))
				.andExpect(model().attribute("event", hasProperty("artists", is(event.getArtists()))))
				.andExpect(model().attribute("event", hasProperty("stage_id", is(eventService.findStages().toArray()[0]))))
				.andExpect(view().name("events/updateEvent"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateEventFormSuccess() throws Exception {
		mockMvc.perform(post("/events/{eventId}/edit",1).param("name", "Magic and Pasion")
				.with(csrf())
				.param("date", "2020/12/27")
				.param("showtype_id", "Magic"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/events"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateGameFormHasErrors() throws Exception {
		mockMvc.perform(post("/events/{eventId}/edit", 1).param("name", "")
							.with(csrf())
							.param("date", "2020/12/27")
							.param("showtype_id", "Magic"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("event"))
				.andExpect(model().attributeHasFieldErrors("event", "name"))
				.andExpect(view().name("events/updateEvent"));
	}
	
	
	
}