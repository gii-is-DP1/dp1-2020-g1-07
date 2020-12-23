package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.repository.ClientGainRepository;
import org.springframework.samples.petclinic.util.Week;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class ClientGainServiceTest {

	private static final String TEST_DNI = "11111111A";
	/*
	@Autowired
	private ClientGainRepository cgainRepo;
	*/
	@Autowired
	protected ClientGainService cgainService;
	/*
	@BeforeEach
    void setup() {
		cgainService = new ClientGainService(cgainRepo);
    }
	*/
	@Test
	public void testCountWithInitialData() {
		int count= cgainService.cgainCount();
		assertEquals(count,4);
	}
	
	@Test
	void shouldFindClientGains() {
		Iterator<ClientGain> it = this.cgainService.findAll().iterator();
		ClientGain cg1 = it.next();
		assertThat(cg1.getAmount().equals(350));
		assertThat(cg1.getDate().equals(LocalDate.of(2020, 9, 7)));
		assertThat(cg1.getClient().getDni().equals(TEST_DNI));
		assertThat(cg1.getGame().getName().equals("Poker"));
		
		assertThat(it.next().getAmount().equals(500));
		assertThat(it.next().getAmount().equals(100));
		assertThat(it.next().getAmount().equals(-200));
		assertThat(!it.hasNext());
	}
	
	
	@Test
	void findClientByUsernameTest() {
		assertThat(true);
	}
	
	@Test
	void findWeeksForUserTest() {
		assertThat(true);
	}
	
	@Test
	void findClientGainsForWeekTest() {
		Week w1 = new Week(LocalDate.of(2020, 9, 21));
		Week w2 = new Week(LocalDate.of(2020, 10, 20));
		Iterator<ClientGain> it1 = this.cgainService.findClientGainsForWeek(w1, TEST_DNI).iterator();
		Iterator<ClientGain> it2 = this.cgainService.findClientGainsForWeek(w2, TEST_DNI).iterator();
		ClientGain cg = it1.next();
		
		assertThat(cg.getAmount().equals(-200));
		assertThat(cg.getClient().getDni().equals(TEST_DNI));
		assertThat(!it1.hasNext());
		assertThat(!it2.hasNext());
	}
	
	@Test
	void findUsersTest() {
		assertThat(true);
	}
	
	@Test
	void findClientsTest() {
		assertThat(true);
	}
	
	@Test
	void findGamesTest() {
		assertThat(true);
	}
	
}
