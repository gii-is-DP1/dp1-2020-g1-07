package org.springframework.samples.petclinic.web;

import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Chef;
import org.springframework.samples.petclinic.service.ChefService;
import org.springframework.samples.petclinic.service.ScheduleService;
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
@RequestMapping("/chefs")
public class ChefController {
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private ScheduleService scheService;
	
	@Autowired
	private ChefValidator validator;
	
	@InitBinder("chef")
	public void initChefBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listChefs(ModelMap modelMap) {
		log.info("Loading list of chefs");
		String view= "chefs/listChef";
		Iterable<Chef> chefs=chefService.findAll();
		modelMap.addAttribute("chefs", chefs);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createChef(ModelMap modelMap) {
		log.info("Loading new chef form");
		String view="chefs/addChef";
		modelMap.addAttribute("chef", new Chef());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveChef(@Valid Chef chef, BindingResult result, ModelMap modelMap) {
		log.info("Saving chef:" + chef.getId());
		String view="chefs/listChef";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("chef", chef);
			return "chefs/addChef";
			
		}else {
			if (validator.getChefwithIdDifferent(chef.getDni(), null)) {
				log.warn("Chef with dni" + chef.getDni() + "already in database");
				result.rejectValue("dni", "dni.duplicate", "Chef with dni" + chef.getDni() + "already in database");
				modelMap.addAttribute("chef", chef);
				return "chefs/addChef";
			}
			
			log.info("Chef validated: saving into DB");
			chefService.save(chef);
			modelMap.addAttribute("message", "Chef successfully saved!");
			view=listChefs(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{chefId}")
	public String deleteChef(@PathVariable("chefId") int chefId, ModelMap modelMap) {
		log.info("Deleting chef:" + chefId);
		String view="chefs/listChef";
		Optional<Chef> chef = chefService.findChefById(chefId);
		if(chef.isPresent()) {
			log.info("Chef found: deleting");
			StreamSupport.stream(scheService.findAll().spliterator(), false)
			.filter(x -> x.getEmp().equals(chef.get()))
			.forEach(x -> scheService.delete(x));
			chefService.delete(chef.get());
			modelMap.addAttribute("message", "Chef successfully deleted!");
			view=listChefs(modelMap);
		}else {
			log.warn("Chef not found in DB: "+ chefId);
			modelMap.addAttribute("message", "Chef not found!");
			view=listChefs(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{chefId}/edit")
    public String initUpdateChefForm(@PathVariable("chefId") int chefId, ModelMap model) {
		log.info("Loading update chef form: " + chefId);
		Chef chef = chefService.findChefById(chefId).get();
        model.put("chef", chef);
        return "chefs/updateChef";
    }

    @PostMapping(value = "/{chefId}/edit")
    public String processUpdateChefForm(@Valid Chef chef, BindingResult result,
            @PathVariable("chefId") int chefId, ModelMap model) {
    	log.info("Updating chefId: " + chefId);
    	chef.setId(chefId);
        if (result.hasErrors()) {
            model.put("chef", chef);
            return "chefs/updateChef";
        }
        else {
        	if (validator.getChefwithIdDifferent(chef.getDni(), chef.getId())) {
        		log.warn("Chef duplicated");
				result.rejectValue("dni", "dni.duplicate", "Chef with dni" + chef.getDni() + "already in database");
				model.addAttribute("chef", chef);
				return "chefs/updateChef";
			}
            this.chefService.save(chef);
            return "redirect:/chefs";
        }
    }
}
