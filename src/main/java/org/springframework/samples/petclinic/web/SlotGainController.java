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
		String view= "slotgains/slotGainsList";
		Iterable<SlotGain> slotGains=slotGainService.findAll();
		modelMap.addAttribute("slotgains", slotGains);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createSlotGain(ModelMap modelMap) {
		String view="slotgains/addSlotGain";
		modelMap.addAttribute("slotGain", new SlotGain());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveSlotGain(@Valid SlotGain slotGain, BindingResult result, ModelMap modelMap) {
		String view="slotgains/slotGainsList";
		if(result.hasErrors()) {
			modelMap.addAttribute("slotGain", slotGain);
			return "slotgains/addSlotGain";
			
		}else {
			slotGainService.save(slotGain);
			modelMap.addAttribute("message", "SlotGain successfully saved!");
			view=slotGainsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{slotGainId}")
	public String deleteSlotGain(@PathVariable("slotGainId") int slotGainId, ModelMap modelMap) {
		String view="slotgains/slotGainsList";
		Optional<SlotGain> slotGain = slotGainService.findSlotGainById(slotGainId);
		if(slotGain.isPresent()) {
			slotGainService.delete(slotGain.get());
			modelMap.addAttribute("message", "SlotGain successfully deleted!");
			view=slotGainsList(modelMap);
		}else {
			modelMap.addAttribute("message", "SlotGain not found!");
			view=slotGainsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{slotGainId}/edit")
	public String initUpdateCasTbForm(@PathVariable("slotGainId") int slotGainId, ModelMap model) {
		SlotGain slotGain = slotGainService.findSlotGainById(slotGainId).get();
		
		model.put("slotGain", slotGain);
		return "slotgains/updateSlotGain";
	}

	@PostMapping(value = "/{slotGainId}/edit")
	public String processUpdateCasTbForm(@Valid SlotGain slotGain, BindingResult result,
			@PathVariable("slotGainId") int slotGainId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("slotGain", slotGain);
			return "slotgains/updateSlotGain";
		}
		else {
			slotGain.setId(slotGainId);
			this.slotGainService.save(slotGain);
			return "redirect:/slotgains";
		}
	}
	
	@ModelAttribute("slotMachines")
	public Collection<SlotMachine> populateSlotMachines() {
		return this.slotGainService.findSlotMachines();
	}

}
