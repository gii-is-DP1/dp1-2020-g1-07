package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.MenuService;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/schedules")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	
	@GetMapping()
	public String listSchedules(ModelMap modelMap) {
		String view= "schedules/listSchedule";
		Iterable<Schedule> schedules=scheduleService.findAll();
		modelMap.addAttribute("schedules", schedules);
		return view;
	}
	
	@ModelAttribute("shifts")
    public Collection<Shift> populateShifts() {
        return this.scheduleService.findShifts();
    }
	
	@ModelAttribute("employees")
	public Collection<Employee> populateEmployees() {
		return this.scheduleService.findEmployees();
	}
	
	@ModelAttribute("employees_dnis")
	public Collection<String> employeeDnis() {
		return this.scheduleService.findScheduleByDni();
	}
	
	@GetMapping(path="/new")
	public String createSchedule(ModelMap modelMap) {
		String view="schedules/addSchedule";
		modelMap.addAttribute("schedule", new Schedule());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveSchedule(@Valid Schedule schedule, BindingResult result, ModelMap modelMap) {
		String view="schedules/listSchedule";
		if(result.hasErrors()) {
			modelMap.addAttribute("schedule", schedule);
			return "schedules/addSchedule";
			
		}else {
			
			scheduleService.save(schedule);
			
			modelMap.addAttribute("message", "Schedule successfully saved!");
			view=listSchedules(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{scheduleId}/edit")
	public String initUpdateCasTbForm(@PathVariable("scheduleId") int scheduleId, ModelMap model) {
		Schedule schedule = scheduleService.findScheduleById(scheduleId).get();
		
		model.put("schedule", schedule);
		return "schedules/updateSchedule";
	}

	@PostMapping(value = "/{scheduleId}/edit")
	public String processUpdateCasTbForm(@Valid Schedule schedule, BindingResult result,
			@PathVariable("scheduleId") int scheduleId, ModelMap model) {
		
		if (result.hasErrors()) {
			model.put("schedule", schedule);
			return "schedules/updateSchedule";
		}
		else {
			schedule.setId(scheduleId);
			this.scheduleService.save(schedule);
			return "redirect:/schedules";
		}
	}
	
	
	@GetMapping(path="/delete/{scheduleId}")
	public String deleteSchedule(@PathVariable("scheduleId") int scheduleId, ModelMap modelMap) {
		String view="schedules/listSchedule";
		Optional<Schedule> schedule = scheduleService.findScheduleById(scheduleId);
		if(schedule.isPresent()) {
			scheduleService.delete(schedule.get());
			modelMap.addAttribute("message", "Schedule successfully deleted!");
			view=listSchedules(modelMap);
		}else {
			modelMap.addAttribute("message", "Schedule not found!");
			view=listSchedules(modelMap);
		}
		return view;
	}
	
}
