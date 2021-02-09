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
	
		Stage stage = new Stage();
		stage.setId(1);
		stage.setCapacity(100);
		
		
		Stage stage2= new Stage();
		stage2.setId(2);
		stage2.setCapacity(130);
		
		List<Stage> stages = new ArrayList<Stage>();
		stages.add(stage);stages.add(stage2);
		given(this.stageService.findAll()).willReturn(stages);
		given(this.stageService.findStagebyId(1)).willReturn(Optional.of(stage));

		
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
						.with(csrf()))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("stages/listStages"));
	}
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/stages/save")
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
				.andExpect(view().name("stages/updateStage"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStageFormSuccess() throws Exception {
		mockMvc.perform(post("/stages/{stageId}/edit", 1)
							.with(csrf())
							.param("capacity", "140"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stages"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStageFormHasErrors() throws Exception {
		mockMvc.perform(post("/stages/{stageId}/edit", 1).param("capacity", "")
							.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("stage"))
				.andExpect(model().attributeHasFieldErrors("stage", "capacity"))
				.andExpect(view().name("stages/updateStage"));
	}
}


