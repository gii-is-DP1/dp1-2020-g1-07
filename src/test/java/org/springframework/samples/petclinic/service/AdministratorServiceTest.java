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
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdministratorServiceTest {

	@Mock
	private AdministratorRepository administratorRepo;
	
	@Autowired
	protected AdministratorService administratorService;
	
	@BeforeEach
    void setup() {
		Administrator admin1 = new Administrator();
		admin1.setId(1);
		admin1.setDni("11111111A");
		admin1.setName("William Carter");
		admin1.setPhone_number("111222333");
		
		Administrator admin2 = new Administrator();
		admin2.setId(2);
		admin2.setDni("22222222B");
		admin2.setName("John Titor");
		admin2.setPhone_number("444555666");
		
		Administrator admin3 = new Administrator();
		admin3.setId(3);
		admin3.setDni("33333333C");
		admin3.setName("Gordon Freeman");
		admin3.setPhone_number("777888999");
		
		given(this.administratorRepo.findAll()).willReturn(Lists.list(admin1, admin2, admin3));
		administratorService = new AdministratorService(administratorRepo);
    }
	
	@Test
	void findAdministratorTest() {
		Iterator<Administrator> it = this.administratorService.findAll().iterator();
		Administrator admin1 = it.next();
		assertThat(admin1.getDni().equals("11111111A"));
		assertThat(admin1.getName().equals("William Carter"));
		assertThat(admin1.getPhone_number().equals("444555666"));
		
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}
