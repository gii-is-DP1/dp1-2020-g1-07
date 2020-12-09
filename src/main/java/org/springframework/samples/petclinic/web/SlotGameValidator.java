package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.SlotGame;
import org.springframework.samples.petclinic.service.SlotGameService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SlotGameValidator implements Validator {
	
	private static final String REQUIRED = "required";
	
	protected SlotGameService slotGameService;


	@Override
	public void validate(Object target, Errors errors) {
		SlotGame slotGame = (SlotGame) target;
		String name = slotGame.getName();
		Integer jackpot = slotGame.getJackpot();
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		if(jackpot == null || jackpot<0) {
			errors.rejectValue("jackpot", REQUIRED + "No puede ser nulo ni negativo", REQUIRED + "No puede ser nulo ni negativo");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SlotGame.class.isAssignableFrom(clazz);
	}

}
