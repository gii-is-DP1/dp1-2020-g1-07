package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.MenuService;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	@GetMapping(path="/user")
	public String listUserSchedules(ModelMap modelMap) {
		String view= "schedules/mySchedule";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails)
			username = ((UserDetails)principal).getUsername();
		else
			username = principal.toString();
		Iterable<Schedule> schedules=scheduleService.findAll();
		List<Schedule> userSchedules = new ArrayList<Schedule>();
		String dni = scheduleService.findUsers().stream()
				.filter(x -> x.getUsername().equals(username)).findFirst().get().getDni();
		for (Schedule sch : schedules)
			if (sch.getEmp().getDni().equals(dni))
				userSchedules.add(sch);
		modelMap.addAttribute("userschedules", userSchedules);
		return view;
	}
	
	@ModelAttribute("shifts")
    public Collection<Shift> populateShifts() {
        return this.scheduleService.findShifts();
    }
	
	@ModelAttribute("employees_ids")
	public Collection<Employee> employeeIds() {
		return this.scheduleService.findEmployeeId();
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
