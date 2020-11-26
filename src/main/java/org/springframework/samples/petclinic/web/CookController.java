package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.service.CookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cooks")
public class CookController {
	@Autowired
	private CookService cookService;
	
	@GetMapping()
	public String listCooks(ModelMap modelMap) {
		String view= "cooks/listCook";
		Iterable<Cook> cooks=cookService.findAll();
		modelMap.addAttribute("cooks", cooks);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createCook(ModelMap modelMap) {
		String view="cooks/addCook";
		modelMap.addAttribute("cook", new Cook());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveCook(@Valid Cook cook, BindingResult result, ModelMap modelMap) {
		String view="cooks/listCook";
		if(result.hasErrors()) {
			modelMap.addAttribute("cook", cook);
			return "cooks/editCook";
			
		}else {
			
			cookService.save(cook);
			
			modelMap.addAttribute("message", "Cook successfully saved!");
			view=listCooks(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{cookId}")
	public String deleteCook(@PathVariable("cookId") int cookId, ModelMap modelMap) {
		String view="cooks/listCook";
		Optional<Cook> cook = cookService.findCookById(cookId);
		if(cook.isPresent()) {
			cookService.delete(cook.get());
			modelMap.addAttribute("message", "Cook successfully deleted!");
			view=listCooks(modelMap);
		}else {
			modelMap.addAttribute("message", "Cook not found!");
			view=listCooks(modelMap);
		}
		return view;
	}
}