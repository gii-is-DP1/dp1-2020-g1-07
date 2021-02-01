package org.springframework.samples.petclinic.web;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= ShowCasinoTableGameController.class,
		includeFilters= @ComponentScan.Filter(value = CasinotableValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
		excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class ShowCasinoTableGameControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CasinotableService casinotabService;
	
	private Casinotable casinotab;
	
	static String startTime=LocalTime.now().minusHours(2).toString().substring(0, 8);
	static String endingTime=LocalTime.now().plusHours(2).toString().substring(0, 8);
	
	@BeforeEach
	void setup() {

		casinotab = new Casinotable();
		casinotab.setId(1);
		casinotab.setName("Mesa test");
		casinotab.setDate(LocalDate.now());
		casinotab.setEndingTime(endingTime);
		casinotab.setStartTime(startTime);
		Game game = new Game();
		game.setId(1);
		game.setName("Poker");
		game.setMaxPlayers(4);
		casinotab.setGame(game);
		GameType gType= new GameType();
		gType.setId(2);
		gType.setName("Cards");
		casinotab.setGametype(gType);
		Skill skill= new Skill();
		skill.setId(1);
		skill.setName("AMATEUR");
		casinotab.setSkill(skill);
		

		given(this.casinotabService.findCasinotableById(1)).willReturn(Optional.of(casinotab));

	}
	@WithMockUser(value = "spring")
    @Test
    void testInitCreation() throws Exception {
		mockMvc.perform(get("/casinotables/showCasinoTableGame")).andExpect(status().isOk())
			.andExpect(view().name("casinotables/showCasinoTableGame")).andExpect(model().attributeExists("casinotables"));
}
}



















