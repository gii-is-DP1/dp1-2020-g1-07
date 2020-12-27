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
	
	public Slotgame getSlotgamewithIdDifferent(String name, Integer id) {
		name = name.toLowerCase();
		Slotgame empty_slotgame = null;
		List<Slotgame> slotgames = StreamSupport.stream(this.slotgameService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Slotgame slotgame : slotgames) {
			String compName = slotgame.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && slotgame.getId()!=id) {
				return slotgame;
			}
		}
		return empty_slotgame;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Slotgame slotgame = (Slotgame) target;
		String name = slotgame.getName();
		Integer jackpot = slotgame.getJackpot();
		Slotgame otherslotgame = null;
		if(name!=null) otherslotgame=getSlotgamewithIdDifferent(name, slotgame.getId());
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		if(otherslotgame!=null) {
			errors.rejectValue("name", "El nombre no puede estar repetido", "El nombre no puede estar repetido");
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
