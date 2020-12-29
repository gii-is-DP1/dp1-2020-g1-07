package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.service.ArtistService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ArtistController.class,
includeFilters = @ComponentScan.Filter(value = ArtistValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ArtistControllerTests {

	private Artist art;
	
	@MockBean
	private ArtistService artistService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.art = new Artist();
		art.setName("Emilio Tejero");
		art.setDni("177013120H");
		art.setPhone_number("999666333");
		
		given(this.artistService.findArtistById(anyInt())).willReturn(Optional.of(art));		
		given(this.artistService.findAll()).willReturn(Lists.list(art));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/artists/new")).andExpect(status().isOk()).andExpect(model().attributeExists("artist"))
		.andExpect(view().name("artists/addArtist"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/artists/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("artists/listArtist"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationRepeatedDni() throws Exception {
		mockMvc.perform(post("/artists/save")
					.with(csrf())
					.param("name", "Gloria Prieto")
					.param("dni", "177013120H")
					.param("phone_number", "957202122"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("artist"))
			.andExpect(model().attributeHasFieldErrors("artist", "dni"))
			.andExpect(view().name("artists/addArtist"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/artists/save")
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270KJJJJ")
						.param("phone_number", "957 - 20 21 22"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("artist"))
			.andExpect(model().attributeHasFieldErrors("artist", "dni"))
			.andExpect(model().attributeHasFieldErrors("artist", "phone_number"))
			.andExpect(view().name("artists/addArtist"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateArtistForm() throws Exception {
		mockMvc.perform(get("/artists/{artistId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("artist"))
				.andExpect(model().attribute("artist", hasProperty("name", is("Emilio Tejero"))))
				.andExpect(model().attribute("artist", hasProperty("dni", is("177013120H"))))
				.andExpect(model().attribute("artist", hasProperty("phone_number", is("999666333"))))
				.andExpect(view().name("artists/updateArtist"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateArtistFormSuccess() throws Exception {
    	mockMvc.perform(post("/artists/{artistId}/edit", 1)
						.with(csrf())
						.param("name", "Gloria Prieto")
						.param("dni", "67947270K")
						.param("phone_number", "957202122"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/artists"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateArtistFormRepeatedDni() throws Exception {
    	mockMvc.perform(post("/artists/{artistId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "177013120H")
				.param("phone_number", "957202122"))
    			.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("artist"))
				.andExpect(model().attributeHasFieldErrors("artist", "dni"))
				.andExpect(view().name("artists/updateArtist"));
	}
    
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateArtistFormHasErrors() throws Exception {
    	mockMvc.perform(post("/artists/{artistId}/edit", 1)
				.with(csrf())
				.param("name", "Gloria Prieto")
				.param("dni", "67947270KJJJJ")
				.param("phone_number", "957 - 20 21 22"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("artist"))
				.andExpect(model().attributeHasFieldErrors("artist", "dni"))
				.andExpect(model().attributeHasFieldErrors("artist", "phone_number"))
				.andExpect(view().name("artists/updateArtist"));
	}
}