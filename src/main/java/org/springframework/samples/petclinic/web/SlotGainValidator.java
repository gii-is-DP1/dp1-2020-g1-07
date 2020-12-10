package org.springframework.samples.petclinic.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotGame;


public class SlotGainValidator implements Validator{
	
	private static final String REQUIRED = "required";

	@Override
	public boolean supports(Class<?> clazz) {
		return SlotGain.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SlotGain slotGain = (SlotGain) target;
		LocalDate date = slotGain.getDate();
		Integer amount = slotGain.getAmount();
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		if(amount == null || amount<=0) {
			errors.rejectValue("amount", REQUIRED + "No puede ser nulo ni negativo", REQUIRED + "No puede ser nulo ni negativo");
		}
	}

}
