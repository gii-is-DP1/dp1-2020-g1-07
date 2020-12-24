package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
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
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SlotMachine.class.isAssignableFrom(clazz);
	}

}
