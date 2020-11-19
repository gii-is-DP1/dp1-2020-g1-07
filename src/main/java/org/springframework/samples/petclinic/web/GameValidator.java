package org.springframework.samples.petclinic.web;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GameValidator implements Validator  {
	private static final String REQUIRED = "required";
	@Override
	public void validate(Object obj, Errors errors) {
		Game game = (Game) obj;
		String name = game.getName();
		Integer maxPlayers = game.getMaxPlayers();
		// name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		if(maxPlayers == null || maxPlayers == 0) {
			errors.rejectValue("maxPlayers", REQUIRED, REQUIRED);
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Game.class.isAssignableFrom(clazz);
	}


	
}
