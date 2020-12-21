package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.service.ClientGainService;
import org.springframework.samples.petclinic.util.Week;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ClientGainController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ClientGainControllerTests {

	private static final String TEST_DATE = "2020-09-07";
	private static final String TEST_JSON = "[{\"id\":1,\"amount\":350,\"date\":\"2020-09-07\",\"dni\":\"11111111A\",\"game\":\"Poker\"},{\"id\":2,\"amount\":500,\"date\":\"2020-09-08\",\"dni\":\"11111111A\",\"game\":\"Poker\"},{\"id\":3,\"amount\":100,\"date\":\"2020-09-08\",\"dni\":\"11111111A\",\"game\":\"BlackJack\"}]";
	
	@MockBean
	private ClientGainService cgainService;
	
	@Autowired
	private MockMvc mockMvc;
	
	SortedSet<Week> userGainsTestSetup() {
		Week w1 = new Week (LocalDate.of(2020, 9, 24));
		Week w2 = new Week (LocalDate.of(2020, 10, 24));
		Week w3 = new Week (LocalDate.of(2020, 11, 24));
		SortedSet<Week> set = Sets.newTreeSet(w1,w2,w3);
		given(this.cgainService.findWeeksForUser()).willReturn(set);
		return set;
	}
	
	@WithMockUser(value = "spring")
    @Test
    void userGainsTest() throws Exception {
		SortedSet<Week> set = userGainsTestSetup();
		mockMvc.perform(get("/cgains/user")).andExpect(status().isOk())
		.andExpect(model().attribute("dates", set));
		
	}
	
	SortedSet<Week> userGainsEmptySetTestSetup() {
		SortedSet<Week> set = new TreeSet<Week>();
		given(this.cgainService.findWeeksForUser()).willReturn(set);
		return set;
	}
	
	@WithMockUser(value = "spring")
    @Test
    void userGainsEmptySetTest() throws Exception {
		SortedSet<Week> set = userGainsEmptySetTestSetup();
		mockMvc.perform(get("/cgains/user")).andExpect(status().isOk())
		.andExpect(model().attribute("dates", set));
	}
	
	void loadUserGainsTestSetup() {
		ClientGain cg1 = new ClientGain();
		cg1.setId(1);
		cg1.setAmount(350);
		cg1.setDate(LocalDate.of(2020, 9, 7));
		cg1.setDni("11111111A");
		cg1.setGame("Poker");
		ClientGain cg2 = new ClientGain();
		cg2.setId(2);
		cg2.setAmount(500);
		cg2.setDate(LocalDate.of(2020, 9, 8));
		cg2.setDni("11111111A");
		cg2.setGame("Poker");
		ClientGain cg3 = new ClientGain();
		cg3.setId(3);
		cg3.setAmount(100);
		cg3.setDate(LocalDate.of(2020, 9, 8));
		cg3.setDni("11111111A");
		cg3.setGame("BlackJack");
		given(this.cgainService.findClientByUsername(anyString())).willReturn("11111111A");
		given(this.cgainService.findClientGainsForWeek(any(Week.class), anyString())).willReturn(Lists.newArrayList(cg1, cg2, cg3));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void loadUserGainsTest() throws Exception {
		loadUserGainsTestSetup();
		mockMvc.perform(get("/cgains/user/{date}", TEST_DATE)).andExpect(status().isOk())
		.andExpect(content().string(TEST_JSON));
	}
	
}
