package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameValidator gameValidator;
	
	@InitBinder("game")
	public void initMenuBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(gameValidator);
	}
	
	@GetMapping()
	public String gamesList(ModelMap modelMap) {
		log.info("Loading list of games view");
		String vista= "games/listGame";
		Iterable<Game> games=gameService.findAll();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String createGame(ModelMap modelMap) {
		log.info("Loading new game form");
		String view="games/addGame";
		modelMap.addAttribute("game", new Game());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveGame(@Valid Game game, BindingResult result, ModelMap modelMap) {
		log.info("Saving cgain: " + game.getId());
		String view="games/listGame";
		if(result.hasErrors()) {
			log.error("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("game", game);
			return "games/addGame";
			
		}else {
			if(gameValidator.gameWithTheSameName(game.getName())){
				log.warn("There is a game with the same name:" + game.getName());
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				modelMap.addAttribute("game", game);
				return "games/addGame";
			}
			log.info("Game validated: saving into DB");
			gameService.save(game);
			modelMap.addAttribute("message", "Game successfully saved!");
			view=gamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{gameId}")
	public String deleteGame(@PathVariable("gameId") int gameId, ModelMap modelMap) {
		String view="games/listGame";
		Optional<Game> game = gameService.findGameById(gameId);
		if(game.isPresent()) {
			if(gameValidator.isUsedInCasinotable(game.get())) {
				log.warn("This game can't be deleted, is in one of the casinotables!");
				modelMap.addAttribute("message", "This game can't be deleted, is in one of the casinotables!");
				view=gamesList(modelMap);
			}else {
			log.info("Game found: deleting");
			gameService.delete(game.get());
			modelMap.addAttribute("message", "Game successfully deleted!");
			view=gamesList(modelMap);
			}
		}else {
			log.warn("Game not found in DB: " + gameId);
			modelMap.addAttribute("message", "Game not found!");
			view=gamesList(modelMap);
		}
		return view;
	}
	@ModelAttribute("gametypes")
    public Collection<GameType> populateGameTypes() {
        return this.gameService.findGameTypes();
    }
	
	@GetMapping(value = "/{gameId}/edit")
	public String initUpdategameForm(@PathVariable("gameId") int gameId, ModelMap model) {
		log.info("Loading update game form");
		Game game = gameService.findGameById(gameId).get();
		model.put("game", game);
		return "games/updateGame";
	}

	@PostMapping(value = "/{gameId}/edit")
	public String processUpdategameForm(@Valid Game game, BindingResult result,
			@PathVariable("gameId") int gameId, ModelMap model) {
		log.info("Updating game: " + gameId);
		game.setId(gameId);
		if (result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			model.put("game", game);
			return "games/updateGame";
		}
		else {
			if(gameValidator.gameWithTheSameName_Update(game.getName(), gameId)){
				log.warn("There is a game with the same name");
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				model.addAttribute("game", game);
				return "games/updateGame";
			}
			log.info("Game validated: updating into DB");
			this.gameService.save(game);
			return "redirect:/games";
		}
	}
}
