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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.service.ClientGainService;
import org.springframework.samples.petclinic.util.Week;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ClientGainController.class,
includeFilters = {	@ComponentScan.Filter(value = ClientGainValidator.class, type = FilterType.ASSIGNABLE_TYPE ),
					@ComponentScan.Filter(value = ClientFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
					@ComponentScan.Filter(value = GameFormatter2.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ClientGainControllerTests {

	private static final String TEST_DATE = "2020-09-07";
	private static final String TEST_JSON = "[{\"id\":1,\"amount\":350,\"date\":\"2020-09-07\",\"dni\":\"11111111A\",\"game\":\"Poker\"},"
											+ "{\"id\":2,\"amount\":500,\"date\":\"2020-09-08\",\"dni\":\"11111111A\",\"game\":\"Poker\"},"
											+ "{\"id\":3,\"amount\":100,\"date\":\"2020-09-08\",\"dni\":\"11111111A\",\"game\":\"BlackJack\"}]";
	
	private Client cl;
	private Game poker;
	private Game bjack;
	private ClientGain cg1;
	
	@MockBean
	private ClientGainService cgainService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		this.cl = new Client();
		cl.setId(1);
		cl.setDni("11111111A");
		this.poker = new Game();
		poker.setId(1);
		poker.setName("Poker");
		this.bjack = new Game();
		bjack.setId(2);
		bjack.setName("BlackJack");
		Casinotable casinoTable = new Casinotable();
		casinoTable.setId(1);
		
		this.cg1 = new ClientGain();
		cg1.setId(1);
		cg1.setAmount(350);
		cg1.setDate(LocalDate.of(2020, 9, 7));
		cg1.setClient(cl);
		cg1.setGame(poker);
		cg1.setTableId(1);
		
		given(this.cgainService.findClients()).willReturn(Lists.list(cl));
		given(this.cgainService.findGames()).willReturn(Lists.list(poker, bjack));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/cgains/new")).andExpect(status().isOk()).andExpect(model().attributeExists("cgain"))
		.andExpect(view().name("cgains/addClientGain"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/cgains/save")
						.with(csrf())
						.param("amount", "250")
						.param("date", "2020/11/24")
						.param("client", cl.getDni())
						.param("game", "Poker")
						.param("tableId", "1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("cgains/listClientGain"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/cgains/save")
						.with(csrf())
						.param("amount", "222")
						.param("date", "2020/11/24")
						.param("client", cl.getDni())
						.param("tableId", "1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("clientGain"))
			.andExpect(model().attributeHasFieldErrors("clientGain", "amount"))
			.andExpect(model().attributeHasFieldErrors("clientGain", "game"))
			.andExpect(view().name("cgains/addClientGain"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateClientGainForm() throws Exception {
		given(this.cgainService.findClientGainById(anyInt())).willReturn(Optional.of(cg1));
		
		mockMvc.perform(get("/cgains/{cgainId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("cgain"))
				.andExpect(model().attribute("cgain", hasProperty("amount", equalTo(350))))
				.andExpect(model().attribute("cgain", hasProperty("date", equalTo(LocalDate.of(2020, 9, 7)))))
				.andExpect(model().attribute("cgain", hasProperty("client", equalTo(cl))))
				.andExpect(model().attribute("cgain", hasProperty("game", equalTo(poker))))
				.andExpect(model().attribute("cgain", hasProperty("tableId", equalTo(1))))
				.andExpect(view().name("cgains/updateClientGain"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateClientGainFormSuccess() throws Exception {
    	mockMvc.perform(post("/cgains/{cgainId}/edit", 1)
						.with(csrf())
						.param("amount", "250")
						.param("date", "2020/11/24")
						.param("client", cl.getDni())
						.param("game", "Poker")
    					.param("tableId", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cgains"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateClientGainFormHasErrors() throws Exception {
    	mockMvc.perform(post("/cgains/{cgainId}/edit", 1)
				.with(csrf())
				.param("amount", "222")
				.param("date", "2020/11/24")
				.param("client", cl.getDni())
    			.param("tableId", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("clientGain"))
				.andExpect(model().attributeHasFieldErrors("clientGain", "amount"))
				.andExpect(model().attributeHasFieldErrors("clientGain", "game"))
				.andExpect(view().name("cgains/updateClientGain"));
	}
	
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
		ClientGain cg2 = new ClientGain();
		cg2.setId(2);
		cg2.setAmount(500);
		cg2.setDate(LocalDate.of(2020, 9, 8));
		cg2.setClient(cl);
		cg2.setGame(poker);
		ClientGain cg3 = new ClientGain();
		cg3.setId(3);
		cg3.setAmount(100);
		cg3.setDate(LocalDate.of(2020, 9, 8));
		cg3.setClient(cl);
		cg3.setGame(bjack);
		given(this.cgainService.findClientByUsername(anyString())).willReturn(cl);
		given(this.cgainService.findClientGainsForWeek(any(Week.class), anyString())).willReturn(Lists.newArrayList(cg1, cg2, cg3));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void loadUserGainsTest() throws Exception {
		loadUserGainsTestSetup();
		mockMvc.perform(get("/cgains/user/{date}", TEST_DATE)).andExpect(status().isOk())
		.andExpect(content().string(TEST_JSON));
	}
	
	void loadUserGainsNoGainsTestSetup() {
		given(this.cgainService.findClientByUsername(anyString())).willReturn(cl);
		given(this.cgainService.findClientGainsForWeek(any(Week.class), anyString())).willReturn(Lists.newArrayList());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void loadUserGainsNoGainsTest() throws Exception {
		loadUserGainsNoGainsTestSetup();
		mockMvc.perform(get("/cgains/user/{date}", TEST_DATE)).andExpect(status().isOk())
		.andExpect(content().string("[]"));
	}
	
}