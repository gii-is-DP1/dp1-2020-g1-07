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
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepo;
	
	@Autowired
	protected EmployeeService employeeService;
	
	@BeforeEach
    void setup() {
		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setDni("11111111A");
		emp1.setName("William Carter");
		emp1.setPhone_number("111222333");
		
		Employee emp2 = new Employee();
		emp2.setId(2);
		emp2.setDni("22222222B");
		emp2.setName("John Titor");
		emp2.setPhone_number("444555666");
		
		Employee emp3 = new Employee();
		emp3.setId(3);
		emp3.setDni("33333333C");
		emp3.setName("Gordon Freeman");
		emp3.setPhone_number("777888999");
		
		given(this.employeeRepo.findAll()).willReturn(Lists.list(emp1, emp2, emp3));
		employeeService = new EmployeeService(employeeRepo);
    }
	
	@Test
	void findEmployeeTest() {
		Iterator<Employee> it = this.employeeService.findAll().iterator();
		Employee emp1 = it.next();
		assertThat(emp1.getDni().equals("11111111A"));
		assertThat(emp1.getName().equals("William Carter"));
		assertThat(emp1.getPhone_number().equals("444555666"));
		
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}
