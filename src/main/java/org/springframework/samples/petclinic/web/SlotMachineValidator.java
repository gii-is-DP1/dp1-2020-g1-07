package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SlotMachineValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object target, Errors errors) {
		SlotMachine slotMachine = (SlotMachine) target;
		Slotgame game = slotMachine.getSlotgame();
		Status status = slotMachine.getStatus();
		if (game == null || game.getName().trim().equals("")) {
			errors.rejectValue("slotgame", REQUIRED, REQUIRED);
		}
		if (status == null || status.getName().trim().equals("")) {
			errors.rejectValue("status", REQUIRED, REQUIRED);
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SlotMachine.class.isAssignableFrom(clazz);
	}

}
