package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CasinotableValidator implements Validator {

	private static final String REQUIRED = "required";

	protected CasinotableService casinotableService;
	
	@Override
	public void validate(Object obj, Errors errors) {
		Casinotable casinotable = (Casinotable) obj;
		Game game = casinotable.getGame();
		GameType gametype = casinotable.getGametype();
		Skill skill = casinotable.getSkill();
		if (game == null || game.getName().trim().equals("")) {
			errors.rejectValue("game", REQUIRED, REQUIRED);
		}
		if (gametype == null ||gametype.getName() == null || gametype.getName().trim().equals("")) {
			errors.rejectValue("gametype", REQUIRED, REQUIRED);
		}
		if (skill == null||skill.getName() == null || skill.getName().trim().equals("")  ) {
			errors.rejectValue("skill", REQUIRED, REQUIRED);
		}
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return Casinotable.class.isAssignableFrom(clazz);
	}
	
}

