package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
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

@Controller
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@InitBinder("game")
	public void initMenuBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new GameValidator());
	}
	
	@GetMapping()
	public String gamesList(ModelMap modelMap) {
		String vista= "games/listGame";
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
		String view="games/listGame";
		if(result.hasErrors()) {
			modelMap.addAttribute("game", game);
			return "games/addGame";
			
		}else {
			
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
			gameService.delete(game.get());
			modelMap.addAttribute("message", "Game successfully deleted!");
			view=gamesList(modelMap);
		}else {
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
		Game game = gameService.findGameById(gameId).get();
		
		model.put("game", game);
		return "games/updateGame";
	}

	@PostMapping(value = "/{gameId}/edit")
	public String processUpdategameForm(@Valid Game game, BindingResult result,
			@PathVariable("gameId") int gameId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("game", game);
			return "games/updateGame";
		}
		else {
			game.setId(gameId);
			this.gameService.save(game);
			return "redirect:/games";
			 /*Optional<Casinotable> casinotableToUpdate=this.castableService.findCasinotableById(casinotableId);
			 Casinotable casinotableToUpdateGet = casinotableToUpdate.get();                                                                            
	                                      
	              this.castableService.save(casinotableToUpdateGet);                    
	                    
	            return "redirect:/casinotables/";*/
		}
	}
}
