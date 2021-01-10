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
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.StageService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= StageController.class,
includeFilters= {@ComponentScan.Filter(value = EventFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = StageValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class StageControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EventService eventService;
	
	@MockBean
	private StageService stageService;
	
	@BeforeEach
	void setup() {
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
		List<Artist> artists = new ArrayList<Artist>();
		artists.add(employee);artists.add(employee2);artists.add(employee3);
		given(this.eventService.findArtists()).willReturn(artists);

		Event event = new Event();
		event.setArtist_id(employee);
		event.setDate(LocalDate.of(2020, 12, 31));
		event.setId(1);
		event.setName("Magic and Pasion");
		event.setShowtype_id(showtype3);
		
		Event event2 = new Event();
		event2.setArtist_id(employee2);
		event2.setDate(LocalDate.of(2019, 11, 23));
		event2.setId(2);
		event2.setName("Hamlet");
		event2.setShowtype_id(showtype2);
		
		List<Event> events = new ArrayList<Event>();
		events.add(event);events.add(event2);
		given(this.eventService.findAll()).willReturn(events);
		given(this.eventService.findEventbyId(1)).willReturn(Optional.of(event));
		Stage stage = new Stage();
		stage.setId(1);
		stage.setCapacity(100);
		stage.setEvent_id(event);
		
		Stage stage2= new Stage();
		stage2.setId(2);
		stage2.setCapacity(130);
		stage2.setEvent_id(event2);
		List<Stage> stages = new ArrayList<Stage>();
		stages.add(stage);stages.add(stage2);
		given(this.stageService.findAll()).willReturn(stages);
		given(this.stageService.findStagebyId(1)).willReturn(Optional.of(stage));
		given(this.stageService.findEvents()).willReturn(events);
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/stages/new")).andExpect(status().isOk()).andExpect(model().attributeExists("stage"))
		.andExpect(view().name("stages/addStage"));
	}
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/stages/save").param("capacity", "120")
						.with(csrf())
						.param("event_id", "Magic and Pasion"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("stages/listStages"));
	}
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/stages/save").param("event_id", "Magic and Pasion")
						.with(csrf())
						.param("capacity", ""))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeHasErrors("stage"))
		.andExpect(model().attributeHasFieldErrors("stage", "capacity"))
		.andExpect(view().name("stages/addStage"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateStageForm() throws Exception {
		mockMvc.perform(get("/stages/{stageId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("stage"))
				.andExpect(model().attribute("stage", hasProperty("capacity", is(stageService.findStagebyId(1).get().getCapacity()))))
				.andExpect(model().attribute("stage", hasProperty("event_id", is(stageService.findStagebyId(1).get().getEvent_id()))))
				.andExpect(view().name("stages/updateStage"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStageFormSuccess() throws Exception {
		mockMvc.perform(post("/stages/{stageId}/edit", 1)
							.with(csrf())
							.param("capacity", "140")
							.param("event_id", "Magic and Pasion"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stages"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStageFormHasErrors() throws Exception {
		mockMvc.perform(post("/stages/{stageId}/edit", 1).param("capacity", "")
							.with(csrf())
							.param("event_id", "Magic and Pasion"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("stage"))
				.andExpect(model().attributeHasFieldErrors("stage", "capacity"))
				.andExpect(view().name("stages/updateStage"));
	}
}


