package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmployeeValidator implements Validator{
	private static final String REQUIRED = "required";
	
	@Autowired
	private EmployeeService employeeService;
	
	public Employee getEmployeewithIdDifferent(String dni, Integer id) {
		Optional<Employee> res = StreamSupport.stream(this.employeeService.findAll().spliterator(), false)
				.filter(x -> x.getDni().equals(dni) && id != null && !x.getId().equals(id))
				.findAny();
		if (res.isPresent())
			return res.get();
		else
			return null;
		
		/*
		List<Employee> employees = StreamSupport.stream(this.employeeService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Employee e : employees) {
			String compDni = e.getName();
			if (compDni.equals(dni)) {
				return e;
			}
		}
		return null;
		*/
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Employee employee = (Employee) target;
		String dni = employee.getDni();
		String name = employee.getName();
		String phone_number = employee.getPhone_number();
		
		//DNI validation
		if (dni == null || dni.trim().equals("") || !Pattern.matches("[0-9]{8}[A-Z]{1}", dni)) {
			errors.rejectValue("dni", REQUIRED + " to contain 8 digits and a single capital letter",
					REQUIRED + " to contain 8 digits and a single capital letter");
		}
		
		//Name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		
		//Phone validation
		if (phone_number == null || phone_number.trim().equals("") || !Pattern.matches("[0-9]{9}", phone_number)) {
			errors.rejectValue("phone_number", REQUIRED + " to contain 9 digits",
					REQUIRED + " to contain 9 digits");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	
}
