package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ScheduleValidator implements Validator{
	private static final String REQUIRED = "required";

	
	@Autowired
	private ScheduleService scheduleService;
	
	public Schedule getSchedulewithIdDifferent(String dni, LocalDate date) {
		Schedule empty_schedule = null;
		List<Schedule> schedules = StreamSupport.stream(this.scheduleService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Schedule schedule : schedules) {
			if (schedule.getEmp().getDni().equals(dni) && schedule.getDate().isEqual(date)) {
				return schedule;
			}
		}
		return empty_schedule;
	}
	@Override
	public void validate(Object obj, Errors errors) {
		Schedule sch = (Schedule) obj;
		LocalDate date = sch.getDate();
		Shift shift = sch.getShift();
		// Date validation
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		//Shift validation
		if (shift == null || shift.getId() < 1 || shift.getId() > 4) {
			errors.rejectValue("shift", REQUIRED, REQUIRED);
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Schedule.class.isAssignableFrom(clazz);
	}
}

