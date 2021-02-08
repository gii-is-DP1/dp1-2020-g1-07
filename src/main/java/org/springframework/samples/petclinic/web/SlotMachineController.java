package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.service.SlotGainService;
import org.springframework.samples.petclinic.service.SlotMachineService;
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
@RequestMapping("/slotmachines")
public class SlotMachineController {
	
	@Autowired
	private SlotMachineService slotMachineService;
	
	@Autowired
	private SlotGainService slotGainService;
	
	@Autowired
	private SlotMachineValidator slotMachineValidator;
	
	@InitBinder("slotMachine")
	public void SlotMachine(WebDataBinder dataBinder) {
		dataBinder.setValidator(slotMachineValidator);
	}
	
	@GetMapping()
	public String slotMachinesList(ModelMap modelMap) {
		log.info("Loading list of slot machines view");
		String view= "slotmachines/slotmachinesList";
		Iterable<SlotMachine> slotMachines=slotMachineService.findAll();
		modelMap.addAttribute("slotMachines", slotMachines);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createSlotMachine(ModelMap modelMap) {
		log.info("Loading new slot machine form");
		String view="slotmachines/addSlotMachine";
		modelMap.addAttribute("slotMachine", new SlotMachine());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveSlotMachine(@Valid SlotMachine slotMachine, BindingResult result, ModelMap modelMap) {
		log.info("Saving slotMachine: " + slotMachine.getId());
		String view="slotmachines/slotmachinesList";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("slotMachine", slotMachine);
			return "slotmachines/addSlotMachine";
			
		}else {
			log.info("Slot machine validated: saving into DB");
			slotMachineService.save(slotMachine);
			modelMap.addAttribute("message", "Slot Machine successfully saved!");
			view=slotMachinesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{slotMachineId}")
	public String deleteSlotMachine(@PathVariable("slotMachineId") int slotMachineId, ModelMap modelMap) {
		log.info("Deleting slot machine: " + slotMachineId);
		String view="slotmachines/slotmachinesList";
		Optional<SlotMachine> slotMachine = slotMachineService.findSlotMachineById(slotMachineId);
		if(slotMachine.isPresent()) {
			//Primero hay que borrar los registros asociados a la slotMachine
			List<SlotGain> slotgains = new ArrayList<SlotGain>(slotMachineService.findGains());
			log.info("First we must delete the slot gains associated with slot machine:" + slotgains);
			for(SlotGain slotgain:slotgains) {
				if(slotgain.getSlotMachine().getId()==slotMachineId) {
					slotGainService.delete(slotgain);
				}
			}
			log.info("Slot games deleted");
			slotMachineService.delete(slotMachine.get());
			log.info("Slot machine deleted");
			modelMap.addAttribute("message", "Slot Machine successfully deleted!");
			view=slotMachinesList(modelMap);
		}else {
			log.warn("Slot machine not found in DB: " + slotMachineId);
			modelMap.addAttribute("message", "Slot Machine not found!");
			view=slotMachinesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{slotMachineId}/edit")
	public String initUpdateSlotMachineForm(@PathVariable("slotMachineId") int slotMachineId, ModelMap model) {
		log.info("Loading update slot machine form");
		SlotMachine slotMachine = slotMachineService.findSlotMachineById(slotMachineId).get();
		
		model.put("slotMachine", slotMachine);
		return "slotmachines/updateSlotMachine";
	}

	@PostMapping(value = "/{slotMachineId}/edit")
	public String processUpdateSlotMachineForm(@Valid SlotMachine slotMachine, BindingResult result,
			@PathVariable("slotMachineId") int slotMachineId, ModelMap model) {
		log.info("Updating slot machine: " + slotMachineId);
		slotMachine.setId(slotMachineId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("slotMachine", slotMachine);
			return "slotmachines/updateSlotMachine";
		}
		else {
			log.info("Slot machine validated: updating into DB");
			this.slotMachineService.save(slotMachine);
			return "redirect:/slotmachines";
		}
	}
	
	@ModelAttribute("statuses")
	public Collection<Status> populateStatus() {
		return this.slotMachineService.findStatus();
	}
	
	@ModelAttribute("slotgames")
	public Collection<Slotgame> populateSlotgame() {
		return this.slotMachineService.findSlotgames();
	}
	
	@ModelAttribute("slotgains")
	public String populateSlotGain() {
		String json = "[";
		try {
			List<SlotGain> slotGains = new ArrayList<SlotGain>(slotMachineService.findGains());
			for(SlotGain slotGain:slotGains) {
				json = json + "{\"id\":" + slotGain.getId() +","
						+ "\"name\":\"" + slotGain.getName() +"\","
						+ "\"date\":\"" + slotGain.getDate() +"\","
						+ "\"amount\":" + slotGain.getAmount() +","
						+ "\"slotMachine\":" + slotGain.getSlotMachine().getId() +"},";
				if(slotGains.indexOf(slotGain)==slotGains.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(slotGains.size()==0) {
				json = json.substring(0, json.length() - 1) + "]";
			}
		}catch(Exception e) {
			System.out.println(slotMachineService.findGains());
		}
		return json;	}

}
