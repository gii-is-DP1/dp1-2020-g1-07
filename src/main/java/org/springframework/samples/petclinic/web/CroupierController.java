package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.service.CroupierService;
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
@RequestMapping("/croupiers")
public class CroupierController {
	
	@Autowired
	private CroupierService croupierService;
	
	@Autowired
	private CroupierValidator validator;
	
	@InitBinder("croupier")
	public void initCroupierBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listCroupiers(ModelMap modelMap) {
		String view= "croupiers/listCroupier";
		Iterable<Croupier> croupiers=croupierService.findAll();
		modelMap.addAttribute("croupiers", croupiers);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createCroupier(ModelMap modelMap) {
		String view="croupiers/addCroupier";
		modelMap.addAttribute("croupier", new Croupier());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveCroupier(@Valid Croupier croupier, BindingResult result, ModelMap modelMap) {
		String view="croupiers/listCroupier";
		if(result.hasErrors()) {
			modelMap.addAttribute("croupier", croupier);
			return "croupiers/addCroupier";
			
		}else {
			if (validator.getCroupierwithIdDifferent(croupier.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Croupier with dni" + croupier.getDni() + "already in database");
				modelMap.addAttribute("croupier", croupier);
				return "croupiers/addCroupier";
			}
			croupierService.save(croupier);
			
			modelMap.addAttribute("message", "Croupier successfully saved!");
			view=listCroupiers(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{croupierId}")
	public String deleteCroupier(@PathVariable("croupierId") int croupierId, ModelMap modelMap) {
		String view="croupiers/listCroupier";
		Optional<Croupier> croupier = croupierService.findCroupierById(croupierId);
		if(croupier.isPresent()) {
			croupierService.delete(croupier.get());
			modelMap.addAttribute("message", "Croupier successfully deleted!");
			view=listCroupiers(modelMap);
		}else {
			modelMap.addAttribute("message", "Croupier not found!");
			view=listCroupiers(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{croupierId}/edit")
    public String initUpdateCroupierForm(@PathVariable("croupierId") int croupierId, ModelMap model) {
		Croupier croupier = croupierService.findCroupierById(croupierId).get();
        model.put("croupier", croupier);
        return "croupiers/updateCroupier";
    }

    @PostMapping(value = "/{croupierId}/edit")
    public String processUpdateCroupierForm(@Valid Croupier croupier, BindingResult result,
            @PathVariable("croupierId") int croupierId, ModelMap model) {
    	croupier.setId(croupierId);
        if (result.hasErrors()) {
            model.put("croupier", croupier);
            return "croupiers/updateCroupier";
        }
        else {
        	if (validator.getCroupierwithIdDifferent(croupier.getDni(), croupier.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Croupier with dni" + croupier.getDni() + "already in database");
				model.addAttribute("croupier", croupier);
				return "croupiers/updateCroupier";
			}
            this.croupierService.save(croupier);
            return "redirect:/croupiers";
        }
    }
    @ModelAttribute("casinotables")
    public Collection<Casinotable> populateCasinotables() {
        return this.croupierService.findCasinotables();
    }
}
