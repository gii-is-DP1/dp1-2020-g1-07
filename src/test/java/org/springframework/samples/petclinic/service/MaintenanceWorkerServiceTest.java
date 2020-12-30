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
import org.springframework.samples.petclinic.model.MaintenanceWorker;
import org.springframework.samples.petclinic.repository.MaintenanceWorkerRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MaintenanceWorkerServiceTest {

	@Mock
	private MaintenanceWorkerRepository mworkerRepo;
	
	@Autowired
	protected MaintenanceWorkerService mworkerService;
	
	@BeforeEach
    void setup() {
		MaintenanceWorker mwk1 = new MaintenanceWorker();
		mwk1.setId(1);
		mwk1.setDni("11111111A");
		mwk1.setName("William Carter");
		mwk1.setPhone_number("111222333");
		
		MaintenanceWorker mwk2 = new MaintenanceWorker();
		mwk2.setId(2);
		mwk2.setDni("22222222B");
		mwk2.setName("John Titor");
		mwk2.setPhone_number("444555666");
		
		MaintenanceWorker mwk3 = new MaintenanceWorker();
		mwk3.setId(3);
		mwk3.setDni("33333333C");
		mwk3.setName("Gordon Freeman");
		mwk3.setPhone_number("777888999");
		
		given(this.mworkerRepo.findAll()).willReturn(Lists.list(mwk1, mwk2, mwk3));
		mworkerService = new MaintenanceWorkerService(mworkerRepo);
    }
	
	@Test
	void findMaintenanceWorkerTest() {
		Iterator<MaintenanceWorker> it = this.mworkerService.findAll().iterator();
		MaintenanceWorker mwk1 = it.next();
		assertThat(mwk1.getDni().equals("11111111A"));
		assertThat(mwk1.getName().equals("William Carter"));
		assertThat(mwk1.getPhone_number().equals("444555666"));
		
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}

