package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Chef;
import org.springframework.samples.petclinic.service.ChefService;
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
@RequestMapping("/chefs")
public class ChefController {
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private ChefValidator validator;
	
	@InitBinder("chef")
	public void initChefBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listChefs(ModelMap modelMap) {
		String view= "chefs/listChef";
		Iterable<Chef> chefs=chefService.findAll();
		modelMap.addAttribute("chefs", chefs);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createChef(ModelMap modelMap) {
		String view="chefs/addChef";
		modelMap.addAttribute("chef", new Chef());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveChef(@Valid Chef chef, BindingResult result, ModelMap modelMap) {
		String view="chefs/listChef";
		if(result.hasErrors()) {
			modelMap.addAttribute("chef", chef);
			return "chefs/editChef";
			
		}else {
			if (validator.getChefwithIdDifferent(chef.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Chef with dni" + chef.getDni() + "already in database");
				modelMap.addAttribute("chef", chef);
				return "chefs/addChef";
			}
			chefService.save(chef);
			
			modelMap.addAttribute("message", "Chef successfully saved!");
			view=listChefs(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{chefId}")
	public String deleteChef(@PathVariable("chefId") int chefId, ModelMap modelMap) {
		String view="chefs/listChef";
		Optional<Chef> chef = chefService.findChefById(chefId);
		if(chef.isPresent()) {
			chefService.delete(chef.get());
			modelMap.addAttribute("message", "Chef successfully deleted!");
			view=listChefs(modelMap);
		}else {
			modelMap.addAttribute("message", "Chef not found!");
			view=listChefs(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{chefId}/edit")
    public String initUpdateChefForm(@PathVariable("chefId") int chefId, ModelMap model) {
		Chef chef = chefService.findChefById(chefId).get();
        model.put("chef", chef);
        return "chefs/updateChef";
    }

    @PostMapping(value = "/{chefId}/edit")
    public String processUpdateChefForm(@Valid Chef chef, BindingResult result,
            @PathVariable("chefId") int chefId, ModelMap model) {
        if (result.hasErrors()) {
            model.put("chef", chef);
            return "chefs/updateChef";
        }
        else {
        	if (validator.getChefwithIdDifferent(chef.getDni(), chef.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Chef with dni" + chef.getDni() + "already in database");
				model.addAttribute("chef", chef);
				return "chefs/updateChef";
			}
        	chef.setId(chefId);
            this.chefService.save(chef);
            return "redirect:/chefs";
        }
    }
}
