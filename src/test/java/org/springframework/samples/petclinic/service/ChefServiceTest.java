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
import org.springframework.samples.petclinic.model.Chef;
import org.springframework.samples.petclinic.repository.ChefRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ChefServiceTest {

	@Mock
	private ChefRepository chefRepo;
	
	@Autowired
	protected ChefService chefService;
	
	@BeforeEach
    void setup() {
		Chef chef1 = new Chef();
		chef1.setId(1);
		chef1.setDni("11111111A");
		chef1.setName("William Carter");
		chef1.setPhone_number("111222333");
		
		Chef chef2 = new Chef();
		chef2.setId(2);
		chef2.setDni("22222222B");
		chef2.setName("John Titor");
		chef2.setPhone_number("444555666");
		
		Chef chef3 = new Chef();
		chef3.setId(3);
		chef3.setDni("33333333C");
		chef3.setName("Gordon Freeman");
		chef3.setPhone_number("777888999");
		
		given(this.chefRepo.findAll()).willReturn(Lists.list(chef1, chef2, chef3));
		chefService = new ChefService(chefRepo);
    }
	
	@Test
	void findChefTest() {
		Iterator<Chef> it = this.chefService.findAll().iterator();
		Chef chef1 = it.next();
		assertThat(chef1.getDni().equals("11111111A"));
		assertThat(chef1.getName().equals("William Carter"));
		assertThat(chef1.getPhone_number().equals("444555666"));
		
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}
