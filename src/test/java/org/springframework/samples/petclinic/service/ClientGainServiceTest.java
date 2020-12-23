package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClientGainRepository;
import org.springframework.samples.petclinic.util.Week;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ClientGainServiceTest {
	
	private static final String TEST_DNI_1 = "11111111A";
	private static final String TEST_DNI_2 = "22222222B";
	private static final String TEST_DNI_3 = "33333333C";
	
	@Mock
	private ClientGainRepository cgainRepo;
	
	@Autowired
	protected ClientGainService cgainService;
	
	@BeforeAll
    void setup() {
		Client cl = new Client();
		cl.setDni(TEST_DNI_1);
		Client cl2 = new Client();
		cl2.setDni(TEST_DNI_2);
		Client cl3 = new Client();
		cl3.setDni(TEST_DNI_3);
		
		User us = new User();
		us.setUsername("client1");
		us.setDni(TEST_DNI_1);
		User us2 = new User();
		us2.setUsername("client2");
		us2.setDni(TEST_DNI_2);
		User us3 = new User();
		us3.setUsername("client3");
		us3.setDni(TEST_DNI_3);
		
		Game poker = new Game();
		poker.setName("Poker");
		Game bjack = new Game();
		bjack.setName("BlackJack");
		
		LocalDate date1 = LocalDate.of(2020, 9, 7);
		LocalDate date2 = LocalDate.of(2020, 9, 8);
		LocalDate date3 = LocalDate.of(2020, 9, 21);
		
		ClientGain cg1 = new ClientGain();
		cg1.setId(1);
		cg1.setAmount(350);
		cg1.setClient(cl);
		cg1.setDate(date1);
		cg1.setGame(poker);
		
		ClientGain cg2 = new ClientGain();
		cg2.setId(2);
		cg2.setAmount(500);
		cg2.setClient(cl);
		cg2.setDate(date2);
		cg2.setGame(poker);
		
		ClientGain cg3 = new ClientGain();
		cg3.setId(3);
		cg3.setAmount(100);
		cg3.setClient(cl);
		cg3.setDate(date2);
		cg3.setGame(bjack);
		
		ClientGain cg4 = new ClientGain();
		cg4.setId(4);
		cg4.setAmount(-200);
		cg4.setClient(cl);
		cg4.setDate(date3);
		cg4.setGame(poker);
		
		given(this.cgainRepo.findAll()).willReturn(Lists.list(cg1, cg2, cg3, cg4));
		given(this.cgainRepo.findClients()).willReturn(Lists.list(cl, cl2, cl3));
		given(this.cgainRepo.findUsers()).willReturn(Lists.list(us, us2, us3));
		given(this.cgainRepo.findDatesForClient(anyString())).willReturn(Lists.emptyList());
		given(this.cgainRepo.findDatesForClient("client1")).willReturn(Lists.list(date1, date2, date3));
		cgainService = new ClientGainService(cgainRepo);
    }
	
	@Test
	void shouldFindClientGains() {
		Iterator<ClientGain> it = this.cgainService.findAll().iterator();
		ClientGain cg1 = it.next();
		assertThat(cg1.getAmount().equals(350));
		assertThat(cg1.getDate().equals(LocalDate.of(2020, 9, 7)));
		assertThat(cg1.getClient().getDni().equals("11111111A"));
		assertThat(cg1.getGame().getName().equals("Poker"));
		
		assertThat(it.next().getAmount().equals(500));
		assertThat(it.next().getAmount().equals(100));
		assertThat(it.next().getAmount().equals(-200));
		assertThat(!it.hasNext());
	}
	
	
	@Test
	void findClientByUsernameTest() {
		assertThat(this.cgainService.findClientByUsername("client1").equals(TEST_DNI_1));
		assertThat(this.cgainService.findClientByUsername("client3").equals(TEST_DNI_3));
		assertThrows(NoSuchElementException.class,() -> this.cgainService.findClientByUsername("another_client"));
	}
	
	@WithMockUser(value = "client1")
	@Test
	void findWeeksForUserTest() {
		SortedSet<Week> weeks = this.cgainService.findWeeksForUser();
		Week w1 = new Week(LocalDate.of(2020, 9, 7));
		Week w2 = new Week(LocalDate.of(2020, 9, 21));
		Week w3 = new Week(LocalDate.of(2020, 6, 7));
		assertThat(weeks.contains(w1));
		assertThat(weeks.contains(w2));
		assertThat(!weeks.contains(w3));
	}
	
	@WithMockUser(value = "client2")
	@Test
	void findNoWeeksForUserTest() {
		SortedSet<Week> weeks = this.cgainService.findWeeksForUser();
		assertThat(weeks.isEmpty());
	}
}
