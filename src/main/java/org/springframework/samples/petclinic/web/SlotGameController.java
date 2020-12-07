package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGame;
import org.springframework.samples.petclinic.service.SlotGameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/slotgames")
public class SlotGameController {
	
	@Autowired
	private SlotGameService slotGameService;
	
	@InitBinder("slotgame")
	public void initslotGameBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new SlotGameValidator());
	}
	
	@GetMapping()
	public String slotGamesList(ModelMap modelMap) {
		String vista= "slotgames/slotgamesList";
		Iterable<SlotGame> slotGames=slotGameService.findAll();
		modelMap.addAttribute("slotgames", slotGames);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String createSlotGame(ModelMap modelMap) {
		String view="slotgames/addSlotGame";
		modelMap.addAttribute("slotgame", new SlotGame());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveSlotGame(@Valid SlotGame slotGame, BindingResult result, ModelMap modelMap) {
		String view="slorgames/slotgamesList";
		if(result.hasErrors()) {
			modelMap.addAttribute("slotgame", slotGame);
			return "slotgames/addSlotGame";
			
		}else {
			slotGameService.save(slotGame);
			modelMap.addAttribute("message", "SlotGame successfully saved!");
			view=slotGamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{slotgameId}")
	public String deleteSlotGame(@PathVariable("slotgameId") int slotGameId, ModelMap modelMap) {
		String view="slotgames/slotgamesList";
		Optional<SlotGame> slotGame = slotGameService.findSlotGameById(slotGameId);
		if(slotGame.isPresent()) {
			slotGameService.delete(slotGame.get());
			modelMap.addAttribute("message", "SlotGame successfully deleted!");
			view=slotGamesList(modelMap);
		}else {
			modelMap.addAttribute("message", "SlotGame not found!");
			view=slotGamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{slotgameId}/edit")
	public String initUpdateCasTbForm(@PathVariable("slotgameId") int slotGameId, ModelMap model) {
		SlotGame slotGame = slotGameService.findSlotGameById(slotGameId).get();
		
		model.put("slotgame", slotGame);
		return "slotgames/updateSlotGame";
	}

	@PostMapping(value = "/{slotgameId}/edit")
	public String processUpdateCasTbForm(@Valid SlotGame slotGame, BindingResult result,
			@PathVariable("slotgameId") int slotGameId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("slotsame", slotGame);
			return "slotgames/updateSlotGame";
		}
		else {
			slotGame.setId(slotGameId);
			this.slotGameService.save(slotGame);
			return "redirect:/slotgames";
		}
	}

}
