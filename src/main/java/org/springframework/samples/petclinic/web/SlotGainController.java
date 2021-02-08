package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.service.SlotGainService;
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
@RequestMapping("/slotgains")
public class SlotGainController {
	@Autowired
	private SlotGainService slotGainService;
	
	@Autowired
	private SlotGainValidator slotGainValidator;
	
	@InitBinder("slotGain")
	public void initSlotGainBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(slotGainValidator);
	}
	
	@GetMapping()
	public String slotGainsList(ModelMap modelMap) {
		log.info("Loading list of slot gains view");
		String view= "slotgains/slotGainsList";
		Iterable<SlotGain> slotGains=slotGainService.findAll();
		modelMap.addAttribute("slotgains", slotGains);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createSlotGain(ModelMap modelMap) {
		log.info("Loading new slot gain form");
		String view="slotgains/addSlotGain";
		modelMap.addAttribute("slotGain", new SlotGain());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveSlotGain(@Valid SlotGain slotGain, BindingResult result, ModelMap modelMap) {
		log.info("Saving slot gain: " + slotGain.getId());
		String view="slotgains/slotGainsList";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("slotGain", slotGain);
			return "slotgains/addSlotGain";
			
		}else {
			
			if(slotGainValidator.getSlotGainwithIdDifferent(slotGain.getSlotMachine(), slotGain.getDate())) {
				log.warn("Slot gain duplicated");
				result.rejectValue("date", "date.duplicate", "Ya hay un registro para esta slot en esta fecha");
				modelMap.addAttribute("slotGain", slotGain);
				return "slotgains/addSlotGain";
			}
			log.info("Slot gain validated: saving into DB");
			slotGainService.save(slotGain);
			modelMap.addAttribute("message", "SlotGain successfully saved!");
			view=slotGainsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{slotGainId}")
	public String deleteSlotGain(@PathVariable("slotGainId") int slotGainId, ModelMap modelMap) {
		log.info("Deleting slot gain: " + slotGainId);
		String view="slotgains/slotGainsList";
		Optional<SlotGain> slotGain = slotGainService.findSlotGainById(slotGainId);
		if(slotGain.isPresent()) {
			log.info("Slot Gain found: deleting");
			slotGainService.delete(slotGain.get());
			modelMap.addAttribute("message", "SlotGain successfully deleted!");
			view=slotGainsList(modelMap);
		}else {
			log.warn("Slot gain not found in DB: " + slotGainId);
			modelMap.addAttribute("message", "SlotGain not found!");
			view=slotGainsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{slotGainId}/edit")
	public String initUpdateSlotGainForm(@PathVariable("slotGainId") int slotGainId, ModelMap model) {
		log.info("Loading update slot gain form");
		SlotGain slotGain = slotGainService.findSlotGainById(slotGainId).get();
		model.put("slotGain", slotGain);
		return "slotgains/updateSlotGain";
	}

	@PostMapping(value = "/{slotGainId}/edit")
	public String processUpdateSlotGainForm(@Valid SlotGain slotGain, BindingResult result,
			@PathVariable("slotGainId") int slotGainId, ModelMap model) {
		log.info("Updating slot gain: " + slotGainId);
		slotGain.setId(slotGainId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("slotGain", slotGain);
			return "slotgains/updateSlotGain";
		}
		else {
			if(slotGainValidator.getSlotGainwithIdDifferent(slotGain.getSlotMachine(), slotGain.getDate(), slotGain.getId())) {
				log.warn("Slot gain duplicated");
				result.rejectValue("date", "date.duplicate", "Ya hay un registro para esta slot en esta fecha");
				model.put("slotGain", slotGain);
				return "slotgains/updateSlotGain";
			}
			log.info("Slot gain validated: updating into DB");
			this.slotGainService.save(slotGain);
			return "redirect:/slotgains";
		}
	}

	@ModelAttribute("slotMachines")
	public Collection<SlotMachine> populateSlotMachines() {
		return this.slotGainService.findSlotMachines();
	}

}
