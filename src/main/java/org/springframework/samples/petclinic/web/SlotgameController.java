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

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/slotgames")
public class SlotgameController {
	
	@Autowired
	private SlotgameService slotgameService;
	
	@Autowired
	private SlotgameValidator slotgameValidator;
	
	@InitBinder("slotgame")
	public void initSlotgameBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(slotgameValidator);
	}
	
	@GetMapping()
	public String slotgamesList(ModelMap modelMap) {
		log.info("Loading list of slot games view");
		String view= "slotgames/slotgamesList";
		Iterable<Slotgame> slotgames=slotgameService.findAll();
		modelMap.addAttribute("slotgames", slotgames);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createSlotgame(ModelMap modelMap) {
		log.info("Loading new slot game form");
		String view="slotgames/addSlotgame";
		modelMap.addAttribute("slotgame", new Slotgame());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveSlotgame(@Valid Slotgame slotgame, BindingResult result, ModelMap modelMap) {
		log.info("Saving slot game: " + slotgame.getId());
		String view="slotgames/slotgamesList";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("slotgame", slotgame);
			return "slotgames/addSlotgame";
			
		}else {
			if(slotgameValidator.getSlotgamewithIdDifferent(slotgame.getName())) {
				log.warn("Slot game duplicated");
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				modelMap.addAttribute("slotgame", slotgame);
				return "slotgames/addSlotgame";
			}
			log.info("Slot game validated: saving into DB");
			slotgameService.save(slotgame);
			modelMap.addAttribute("message", "SlotGame successfully saved!");
			view=slotgamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{slotgameId}")
	public String deleteSlotgame(@PathVariable("slotgameId") int slotgameId, ModelMap modelMap) {
		log.info("Deleting slot game: " + slotgameId);
		String view="slotgames/slotgamesList";
		Optional<Slotgame> slotgame = slotgameService.findSlotgameById(slotgameId);
		if(slotgame.isPresent()) {
			if(slotgameValidator.isUsedInSlotMachine(slotgame)) {
				log.info("Slot game duplicated");
				modelMap.addAttribute("message", "This game can't be deleted, is in one of the slots!");
				view=slotgamesList(modelMap);
			}else {
				log.info("Slot game found: deleting");
				slotgameService.delete(slotgame.get());
				modelMap.addAttribute("message", "SlotGame successfully deleted!");
				view=slotgamesList(modelMap);
			}
		}else {
			log.warn("Slot game not found in DB: " + slotgameId);
			modelMap.addAttribute("message", "SlotGame not found!");
			view=slotgamesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{slotgameId}/edit")
	public String initUpdateSlotGameForm(@PathVariable("slotgameId") int slotgameId, ModelMap model) {
		log.info("Loading update slot game form");
		Slotgame slotgame = slotgameService.findSlotgameById(slotgameId).get();
		model.put("slotgame", slotgame);
		return "slotgames/updateSlotgame";
	}

	@PostMapping(value = "/{slotgameId}/edit")
	public String processUpdateCasTbForm(@Valid Slotgame slotgame, BindingResult result,
			@PathVariable("slotgameId") int slotgameId, ModelMap model) {
		log.info("Updating slot game: " + slotgameId);
		slotgame.setId(slotgameId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("slotgame", slotgame);
			return "slotgames/updateSlotgame";
		}
		else {
			if(slotgameValidator.getSlotgamewithIdDifferent(slotgame.getName(), slotgame.getId())) {
				log.warn("Slot game duplicated");
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				model.put("slotgame", slotgame);
				return "slotgames/updateSlotgame";
			}
			log.info("Slot game validated: updating into DB");
			this.slotgameService.save(slotgame);
			return "redirect:/slotgames";
		}
	}

}
