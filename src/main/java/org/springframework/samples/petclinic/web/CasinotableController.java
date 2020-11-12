package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/casinotables")
public class CasinotableController {

	@Autowired
	private CasinotableService castableService;
	
	@GetMapping()
	public String casinotablesListed(ModelMap modelMap) {
		String view = "casinotables/listCasinotable";
		Iterable<Casinotable> casinotables=castableService.findAll();
		modelMap.addAttribute("casinotables", casinotables);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createCasinotable(ModelMap modelMap) {
		String view="casinotables/addCasinotable";
		modelMap.addAttribute("casinotable", new Casinotable());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveCasinotable(@Valid Casinotable casinotable, BindingResult result, ModelMap modelMap) {
		String view="casinotables/listCasinotable";
		if(result.hasErrors()) {
			modelMap.addAttribute("casinotable", casinotable);
			return "casinotables/editCasinotable";
			
		}else {
			
			castableService.save(casinotable);
			
			modelMap.addAttribute("message", "Casinotable successfully saved!");
			view=casinotablesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{casinotableId}")
	public String borrarCasinotable(@PathVariable("casinotableId") int casinotableId, ModelMap modelMap) {
		String view="casinotables/listCasinotable";
		Optional<Casinotable> casinotable = castableService.findCasinotableById(casinotableId);
		if(casinotable.isPresent()) {
			castableService.delete(casinotable.get());
			modelMap.addAttribute("message", "Casinotable successfully deleted!");
			view=casinotablesListed(modelMap);
		}else {
			modelMap.addAttribute("message", "Casinotable not found!");
			view=casinotablesListed(modelMap);
		}
		return view;
	}
}
