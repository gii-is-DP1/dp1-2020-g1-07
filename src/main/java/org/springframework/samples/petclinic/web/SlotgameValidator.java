package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SlotgameValidator implements Validator {
	
	private static final String REQUIRED = "required";

	@Override
	public void validate(Object target, Errors errors) {
		Slotgame slotgame = (Slotgame) target;
		String name = slotgame.getName();
		Integer jackpot = slotgame.getJackpot();
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		if(jackpot == null || jackpot<0) {
			errors.rejectValue("jackpot", REQUIRED + "No puede ser nulo ni negativo", REQUIRED + "No puede ser nulo ni negativo");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Slotgame.class.isAssignableFrom(clazz);
	}

}
