package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.repository.ClientGainRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class ClientGainServiceTest {

	@Mock
	private ClientGainRepository cgainRepo;
	
	protected ClientGainService cgainService;
	
	@BeforeAll
    void setup() {
		cgainService = new ClientGainService(cgainRepo);
    }
	
	@Test
	public void testCountWithInitialData() {
		int count= cgainService.cgainCount();
		assertEquals(count,4);
	}
	
	@Test
	void shouldFindVets() {
		Iterable<ClientGain> cgains = this.cgainService.findAll();
		Iterator<ClientGain> it = cgains.iterator();
		ClientGain cg1 = it.next();
		assertThat(cg1.getAmount().equals(350));
		assertThat(cg1.getDate().equals(LocalDate.of(2020, 9, 7)));
		assertThat(cg1.getClient().getDni().equals("11111111A"));
		assertThat(cg1.getGame().getName().equals("Poker"));
		
		assertThat(it.next().getAmount().equals(500));
		assertThat(it.next().getAmount().equals(100));
		assertThat(it.next().getAmount().equals(-200));
	}
	
	/*
	@Test
	void findClientByUsernameTest() {
		
	}
	
	@Test
	void findWeeksForUserTest() {
		
	}
    
	@Test
	void findClientGainsForWeekTest() {
		
	}
	
	@Test
	void findUsersTest() {
		
	}
	
	@Test
	void findClientsTest() {
		
	}
	
	@Test
	void findGamesTest() {
		
	}
	*/
}
