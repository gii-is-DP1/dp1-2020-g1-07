package org.springframework.samples.petclinic.web;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeValidator validator;
	
	@InitBinder("employee")
	public void initClientGainBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listEmployees(ModelMap modelMap) {
		String view= "employees/listEmployee";
		Iterable<Employee> employees=employeeService.findAll();
		modelMap.addAttribute("employees", employees);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createEmployee(ModelMap modelMap) {
		String view="employees/addEmployee";
		modelMap.addAttribute("employee", new Employee());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveEmployee(@Valid Employee employee, BindingResult result, ModelMap modelMap) {
		String view="employees/listEmployee";
		if(result.hasErrors()) {
			modelMap.addAttribute("employee", employee);
			return "employees/addEmployee";
			
		}else {
			if (validator.getEmployeewithIdDifferent(employee.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Employee with dni" + employee.getDni() + "already in database");
				modelMap.addAttribute("employee", employee);
				return "employees/addEmployee";
			}
			employeeService.save(employee);
			
			modelMap.addAttribute("message", "Employee successfully saved!");
			view=listEmployees(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") int employeeId, ModelMap modelMap) {
		String view="employees/listEmployee";
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
	

	@GetMapping(value = "/{employeeId}/edit")
    public String initUpdateEmployeeForm(@PathVariable("employeeId") int employeeId, ModelMap model) {
		Employee employee = employeeService.findEmployeeById(employeeId).get();
        model.put("employee", employee);
        return "employees/updateEmployee";
    }

    @PostMapping(value = "/{employeeId}/edit")
    public String processUpdateEmployeeForm(@Valid Employee employee, BindingResult result,
            @PathVariable("employeeId") int employeeId, ModelMap model) {
        if (result.hasErrors()) {
            model.put("employee", employee);
            return "employees/updateEmployee";
        }
        else {
        	if (validator.getEmployeewithIdDifferent(employee.getDni(), employee.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Employee with dni" + employee.getDni() + "already in database");
				model.addAttribute("employee", employee);
				return "employees/updateEmployee";
			}
        	employee.setId(employeeId);
            this.employeeService.save(employee);
            return "redirect:/employees";
        }
    }
}