package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.samples.petclinic.model.Game;

import org.springframework.samples.petclinic.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping()
	public String gamesList(ModelMap modelMap) {
		String vista= "games/gamesList";
		Iterable<Game> games=gameService.findAll();
		modelMap.addAttribute("games", games);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String createGame(ModelMap modelMap) {
		String view="games/addGame";
		modelMap.addAttribute("game", new Game());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveGame(@Valid Game game, BindingResult result, ModelMap modelMap) {
		String view="games/gamesList";
		if(result.hasErrors()) {
			modelMap.addAttribute("game", game);
			return "games/editGame";
			
		}else {
			
			gameService.save(game);
			
			modelMap.addAttribute("message", "Game successfully saved!");
			view=gamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{gameID}")
	public String deleteGame(@PathVariable("gameID") int gameID, ModelMap modelMap) {
		String view="games/gamesList";
		Optional<Game> game = gameService.findGameById(gameID);
		if(game.isPresent()) {
			gameService.delete(game.get());
			modelMap.addAttribute("message", "Game successfully deleted!");
			view=gamesList(modelMap);
		}else {
			modelMap.addAttribute("message", "Game not found!");
			view=gamesList(modelMap);
		}
		return view;
	}
	
}
