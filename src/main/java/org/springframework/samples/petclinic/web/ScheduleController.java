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
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.samples.petclinic.util.UserUtils;
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

import lombok.extern.slf4j.Slf4j;
@Slf4j

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ScheduleValidator scheduleValidator;
	
	@InitBinder("schedule")
	public void initScheduleBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(scheduleValidator);
	}
	
	@GetMapping()
	public String listSchedules(ModelMap modelMap) {
		log.info("Loading list of croupiers");
		String view= "schedules/listSchedule";
		Iterable<Schedule> schedules=scheduleService.findAll();
		modelMap.addAttribute("schedules", schedules);
		return view;
	}
	
	@GetMapping(path="/user")
	public String listUserSchedules(ModelMap modelMap) {
		log.info("Loading list of croupiers");
		String view= "schedules/mySchedule";
		String username = UserUtils.getUser();
		Iterable<Schedule> schedules=scheduleService.findAll();
		List<Schedule> userSchedules = new ArrayList<Schedule>();
		String dni = scheduleService.findEmployeeByUsername(username).getDni();
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
			if(scheduleValidator.getSchedulewithIdDifferent(schedule.getEmp().getDni(),schedule.getDate()) != null){
				result.rejectValue("date", "date.duplicate", "User schedule already exist.");
				modelMap.addAttribute("schedule", schedule);
				return "schedules/addSchedule";
			}
			
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
		schedule.setId(scheduleId);
		if (result.hasErrors()) {
			model.put("schedule", schedule);
			return "schedules/updateSchedule";
		}
		else {
			if(scheduleValidator.getSchedulewithIdDifferent(schedule.getEmp().getDni(),schedule.getDate(),schedule.getId()) != null){
				result.rejectValue("date", "date.duplicate", "User schedule already exist.");
				model.addAttribute("schedule", schedule);
				return "schedules/updateSchedule";
			}
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
