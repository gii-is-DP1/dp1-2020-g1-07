package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.service.SlotgameService;
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
public class SlotgameController {
	
	@Autowired
	private SlotgameService slotgameService;
	
	@InitBinder("slotgame")
	public void initSlotgameBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new SlotgameValidator());
	}
	
	@GetMapping()
	public String slotgamesList(ModelMap modelMap) {
		String view= "slotgames/slotgamesList";
		Iterable<Slotgame> slotgames=slotgameService.findAll();
		modelMap.addAttribute("slotgames", slotgames);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createSlotgame(ModelMap modelMap) {
		String view="slotgames/addSlotgame";
		modelMap.addAttribute("slotgame", new Slotgame());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveSlotgame(@Valid Slotgame slotgame, BindingResult result, ModelMap modelMap) {
		String view="slotgames/slotgamesList";
		if(result.hasErrors()) {
			modelMap.addAttribute("slotgame", slotgame);
			return "slotgames/addSlotgame";
			
		}else {
			slotgameService.save(slotgame);
			modelMap.addAttribute("message", "SlotGame successfully saved!");
			view=slotgamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{slotgameId}")
	public String deleteSlotgame(@PathVariable("slotgameId") int slotgameId, ModelMap modelMap) {
		String view="slotgames/slotgamesList";
		Optional<Slotgame> slotgame = slotgameService.findSlotgameById(slotgameId);
		if(slotgame.isPresent()) {
			slotgameService.delete(slotgame.get());
			modelMap.addAttribute("message", "SlotGame successfully deleted!");
			view=slotgamesList(modelMap);
		}else {
			modelMap.addAttribute("message", "SlotGame not found!");
			view=slotgamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{slotgameId}/edit")
	public String initUpdateCasTbForm(@PathVariable("slotgameId") int slotgameId, ModelMap model) {
		Slotgame slotgame = slotgameService.findSlotgameById(slotgameId).get();
		
		model.put("slotgame", slotgame);
		return "slotgames/updateSlotgame";
	}

	@PostMapping(value = "/{slotgameId}/edit")
	public String processUpdateCasTbForm(@Valid Slotgame slotgame, BindingResult result,
			@PathVariable("slotgameId") int slotgameId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("slotgame", slotgame);
			return "slotgames/updateSlotgame";
		}
		else {
			slotgame.setId(slotgameId);
			this.slotgameService.save(slotgame);
			return "redirect:/slotgames";
		}
	}

}
