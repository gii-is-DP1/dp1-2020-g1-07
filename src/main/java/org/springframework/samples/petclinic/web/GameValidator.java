package org.springframework.samples.petclinic.web;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.samples.petclinic.service.GameService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GameValidator implements Validator  {
	private static final String REQUIRED = "required";
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private CasinotableService casinotableService;
	
	public Boolean gameWithTheSameName(String name) {
		name = name.toLowerCase();
		Boolean result = false;
		List<Game> games = StreamSupport.stream(this.gameService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Game game : games) {
			String namegame = game.getName();
			namegame = namegame.toLowerCase();
			if (namegame.equals(name)) {
				result = true;
			}
		}
		return result;
	}
	public Boolean gameWithTheSameName_Update(String name, Integer id) {
		name = name.toLowerCase();
		Boolean result = false;
		List<Game> games = StreamSupport.stream(this.gameService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Game game : games) {
			String namegame = game.getName();
			namegame = namegame.toLowerCase();
			if (namegame.equals(name) && game.getId()!=id) {
				result = true;
			}
		}
		return result;
	}
	public boolean isUsedInCasinotable(Game game) {
		boolean result = false;
		List<Casinotable> casinotables = StreamSupport.stream(this.casinotableService.findAll().spliterator(), false).collect(Collectors.toList());
		for(Casinotable casinotable: casinotables) {
			String gameName = game.getName();
			String gameCasinotable = casinotable.getGame().getName();
			
			if(gameName.equals(gameCasinotable)) result = true;
		}
		return result;
	}
	@Override
	public void validate(Object obj, Errors errors) {
		Game game = (Game) obj;
		String name = game.getName();
		Integer maxPlayers = game.getMaxPlayers();
		// name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		if( maxPlayers == null  || maxPlayers == 0) {
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
