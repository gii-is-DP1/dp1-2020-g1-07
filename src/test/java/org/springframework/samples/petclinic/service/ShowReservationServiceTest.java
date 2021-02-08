package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import java.util.Iterator;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.repository.ShowReservationRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ShowReservationServiceTest {

	@Mock
	private ShowReservationRepository showresRepo;
	
	protected ShowReservationService showresServ;
	
	@BeforeEach
    void setup() {
		Client cl = new Client();
		cl.setDni("11111111A");
		cl.setName("Roy Mustang");
		
		ShowReservation sr1 = new ShowReservation();
		sr1.setId(1);
		sr1.setSeats(10);
		sr1.setClient(cl);
		
		ShowReservation sr2 = new ShowReservation();
		sr2.setId(2);
		sr2.setSeats(20);
		sr2.setClient(cl);
		
		ShowReservation sr3 = new ShowReservation();
		sr3.setId(3);
		sr3.setSeats(30);
		sr3.setClient(cl);
		
		given(this.showresRepo.findAll()).willReturn(Lists.list(sr1,sr2,sr3));
		given(this.showresRepo.findTotalSeats(anyInt())).willReturn(40);
		given(this.showresRepo.findReservedSeats(anyInt())).willReturn(20);
		this.showresServ = new ShowReservationService(showresRepo);
	}
	
	@Test
	void findShowres() {
		Iterator<ShowReservation> it = this.showresServ.findAll().iterator();
		ShowReservation sr1 = it.next();
		assertEquals(sr1.getClient().getDni(),"11111111A");
		assertEquals(sr1.getClient().getName(),"Roy Mustang");
		assertEquals(sr1.getSeats(),10);
		
		assertEquals(it.next().getSeats(),20);
		assertEquals(it.next().getSeats(),30);
		assertThat(!it.hasNext());
	}
	
	@Test
	void findAvailableSeats() {
		assertEquals(this.showresServ.findAvailableSeats(5),20);
	}
	
}