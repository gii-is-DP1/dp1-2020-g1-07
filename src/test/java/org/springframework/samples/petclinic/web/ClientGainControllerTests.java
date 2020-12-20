package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class ClientGainControllerTests {

	private static final int TEST_CGAIN_ID = 1;
	private static final String TEST_DATE = "2020-09-07";
	private static final String TEST_JSON = "[{\"id\":1,\"amount\":350,\"date\":\"2020-09-07\",\"dni\":\"11111111A\",\"game\":\"Poker\"},{\"id\":2,\"amount\":500,\"date\":\"2020-09-08\",\"dni\":\"11111111A\",\"game\":\"Poker\"},{\"id\":3,\"amount\":100,\"date\":\"2020-09-08\",\"dni\":\"11111111A\",\"game\":\"BlackJack\"}]";
	
	@Autowired
	private ClientGainController cgainController;
	
	@MockBean
	private ClientGainService cgainService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		ClientGain cg1 = new ClientGain();
		cg1.setAmount(350);
		cg1.setDate(LocalDate.of(2020, 7, 9));
		cg1.setDni("11111111A");
		cg1.setGame("Poker");
		ClientGain cg2 = new ClientGain();
		cg2.setAmount(500);
		cg2.setDate(LocalDate.of(2020, 8, 9));
		cg2.setDni("11111111A");
		cg2.setGame("Poker");
		ClientGain cg3 = new ClientGain();
		cg3.setAmount(100);
		cg3.setDate(LocalDate.of(2020, 8, 9));
		cg3.setDni("11111111A");
		cg3.setGame("BlackJack");
		Week dummy = new Week(LocalDate.now());
		given(this.cgainService.findClients()).willReturn(Lists.newArrayList("11111111A"));
		given(this.cgainService.findClientGainsForWeek(eq(dummy), anyString())).willReturn(Lists.newArrayList(cg1, cg2, cg3));
	}
	
	/*
	@WithMockUser(value = "spring")
    @Test
    void loadUserGainsTest() throws Exception {
		mockMvc.perform(get("/cgains/user/{date}", TEST_DATE)).andExpect(status().isOk())
		.andExpect(content().string(TEST_JSON));
	}
	*/
}
