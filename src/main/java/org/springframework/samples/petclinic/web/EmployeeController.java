package org.springframework.samples.petclinic.web;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping()
	public String listEmployees(ModelMap modelMap) {
		String vista= "employee/listEmployee";
		Iterable<Employee> employees=employeeService.findAll();
		modelMap.addAttribute("employee", employees);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String createEmployee(ModelMap modelMap) {
		String view="employee/addEmployee";
		modelMap.addAttribute("employee", new Employee());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveEmployee(@Valid Employee employee, BindingResult result, ModelMap modelMap) {
		String view="employee/listEmployee";
		if(result.hasErrors()) {
			modelMap.addAttribute("employee", employee);
			return "employee/editEmployee";
			
		}else {
			
			employeeService.save(employee);
			
			modelMap.addAttribute("message", "Employee successfully saved!");
			view=listEmployees(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") String employeeId, ModelMap modelMap) {
		String view="employee/listEmployee";
		Optional<Employee> employee = employeeService.findEmployeeById(employeeId);
		if(employee.isPresent()) {
			employeeService.delete(employee.get());
			modelMap.addAttribute("message", "Employee successfully deleted!");
			view=listEmployees(modelMap);
		}else {
			modelMap.addAttribute("message", "Employee not found!");
			view=listEmployees(modelMap);
		}
		return view;
	}
}