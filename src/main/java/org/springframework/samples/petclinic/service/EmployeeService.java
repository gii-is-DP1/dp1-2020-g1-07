package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {
	
	private  EmployeeRepository employeeRep;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRep) {
		this.employeeRep = employeeRep;
	}
	
	@Transactional
	public int employeeCount() {
		return (int)employeeRep.count();
	}
	
	@Transactional
	public Iterable<Employee> findAll() {
		return employeeRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Employee> findEmployeeById(int id){ 
		return employeeRep.findById(id);
	}

	@Transactional
	public  void save(Employee employee) {   
		employeeRep.save(employee);
	}

	public  void delete(Employee employee) { 
		employeeRep.delete(employee);
	}
	
	public Employee findEmployeeForDni(String dni) {
		log.info("Loading employee with DNI: " + dni);
		return employeeRep.findEmployeeForDni(dni);
	}
}
