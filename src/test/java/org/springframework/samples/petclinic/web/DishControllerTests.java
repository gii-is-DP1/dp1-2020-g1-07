package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers= DishController.class,
		includeFilters= {@ComponentScan.Filter(value = DishCourseFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
						@ComponentScan.Filter(value = ShiftFormatter.class, type = FilterType.ASSIGNABLE_TYPE )},
		excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class DishControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DishService dishService;
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/dishes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("dish"))
		.andExpect(view().name("dishes/addDish"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/dishes/save").param("name", "Macarrones")
						.with(csrf())
						.param("dish_course", "First")
						.param("shift", "Day"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/dishes/save")
						.with(csrf())
						.param("dish_course", "First")
						.param("shift", "Day"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("dish"))
			.andExpect(model().attributeHasFieldErrors("dish", "name"))
			.andExpect(view().name("dishes/addDish"));
	}

}
