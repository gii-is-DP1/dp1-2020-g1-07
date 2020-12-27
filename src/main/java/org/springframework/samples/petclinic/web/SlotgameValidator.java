package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.service.SlotgameService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SlotgameValidator implements Validator {
	
	private static final String REQUIRED = "required";

	@Autowired
	private SlotgameService slotgameService;
	
	public Boolean getSlotgamewithIdDifferent(String name) {
		name = name.toLowerCase();
		Boolean result = false;
		List<Slotgame> slotgames = StreamSupport.stream(this.slotgameService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Slotgame slotgame : slotgames) {
			String compName = slotgame.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name)) {
				result = true;
			}
		}
		return result;
	}
	
	public Boolean getSlotgamewithIdDifferent(String name, Integer id) {
		name = name.toLowerCase();
		Boolean result = false;
		List<Slotgame> slotgames = StreamSupport.stream(this.slotgameService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Slotgame slotgame : slotgames) {
			String compName = slotgame.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && slotgame.getId()!=id) {
				result = true;
			}
		}
		return result;
	}
	
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
