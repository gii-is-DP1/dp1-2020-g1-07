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
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.repository.CookRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CookServiceTest {

	@Mock
	private CookRepository cookRepo;
	
	@Autowired
	protected CookService cookService;
	
	@BeforeEach
    void setup() {
		Cook cook1 = new Cook();
		cook1.setId(1);
		cook1.setDni("11111111A");
		cook1.setName("William Carter");
		cook1.setPhone_number("111222333");
		
		Cook cook2 = new Cook();
		cook2.setId(2);
		cook2.setDni("22222222B");
		cook2.setName("John Titor");
		cook2.setPhone_number("444555666");
		
		Cook cook3 = new Cook();
		cook3.setId(3);
		cook3.setDni("33333333C");
		cook3.setName("Gordon Freeman");
		cook3.setPhone_number("777888999");
		
		given(this.cookRepo.findAll()).willReturn(Lists.list(cook1, cook2, cook3));
		cookService = new CookService(cookRepo);
    }
	
	@Test
	void findCookTest() {
		Iterator<Cook> it = this.cookService.findAll().iterator();
		Cook cook1 = it.next();
		assertThat(cook1.getDni().equals("11111111A"));
		assertThat(cook1.getName().equals("William Carter"));
		assertThat(cook1.getPhone_number().equals("444555666"));
		
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}