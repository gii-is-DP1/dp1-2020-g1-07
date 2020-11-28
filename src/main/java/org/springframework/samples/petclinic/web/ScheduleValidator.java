package org.springframework.samples.petclinic.web;

import java.time.LocalDate;


import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ScheduleValidator implements Validator{
	private static final String REQUIRED = "required";

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
