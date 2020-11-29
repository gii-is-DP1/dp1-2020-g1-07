package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.service.ScheduleService;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Employee;


import org.springframework.stereotype.Component;

@Component
public class EmployeeFormatter implements Formatter<Employee>{
	private final ScheduleService scheduleService;
	   
    @Autowired
    public EmployeeFormatter(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    @Override
    public String print(Employee employee, Locale locale) {
        return employee.getDni();
    }
    @Override
    public Employee parse(String text, Locale locale) throws ParseException {
        Collection<Employee> findEmployees = this.scheduleService.findEmployees();
        for (Employee employee : findEmployees) {
            if (employee.getDni().equals(text)) {
                return employee;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}
