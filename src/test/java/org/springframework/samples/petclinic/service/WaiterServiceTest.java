package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Iterator;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.repository.WaiterRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class WaiterServiceTest {

	@Mock
	private WaiterRepository waiterRepo;
	
	@Autowired
	protected WaiterService waiterService;
	
	@BeforeEach
    void setup() {
		Waiter waiter1 = new Waiter();
		waiter1.setId(1);
		waiter1.setDni("11111111A");
		waiter1.setName("William Carter");
		waiter1.setPhone_number("111222333");
		
		Waiter waiter2 = new Waiter();
		waiter2.setId(2);
		waiter2.setDni("22222222B");
		waiter2.setName("John Titor");
		waiter2.setPhone_number("444555666");
		
		Waiter waiter3 = new Waiter();
		waiter3.setId(3);
		waiter3.setDni("33333333C");
		waiter3.setName("Gordon Freeman");
		waiter3.setPhone_number("777888999");
		
		given(this.waiterRepo.findAll()).willReturn(Lists.list(waiter1, waiter2, waiter3));
		waiterService = new WaiterService(waiterRepo);
    }
	
	@Test
	void findWaiterTest() {
		Iterator<Waiter> it = this.waiterService.findAll().iterator();
		Waiter waiter1 = it.next();
		assertThat(waiter1.getDni().equals("11111111A"));
		assertThat(waiter1.getName().equals("William Carter"));
		assertThat(waiter1.getPhone_number().equals("444555666"));
		
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}
