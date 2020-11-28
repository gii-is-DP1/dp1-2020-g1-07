package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.service.AdministratorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrators")
public class AdministratorController {
	@Autowired
	private AdministratorService administratorService;
	
	@GetMapping()
	public String listAdministrators(ModelMap modelMap) {
		String view= "administrators/listAdministrator";
		Iterable<Administrator> administrators=administratorService.findAll();
		modelMap.addAttribute("administrators", administrators);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createAdministrator(ModelMap modelMap) {
		String view="administrators/addAdministrator";
		modelMap.addAttribute("administrator", new Administrator());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveAdministrator(@Valid Administrator administrator, BindingResult result, ModelMap modelMap) {
		String view="administrators/listAdministrator";
		if(result.hasErrors()) {
			modelMap.addAttribute("administrator", administrator);
			return "administrators/editAdministrator";
			
		}else {
			
			administratorService.save(administrator);
			
			modelMap.addAttribute("message", "Administrator successfully saved!");
			view=listAdministrators(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{administratorId}")
	public String deleteAdministrator(@PathVariable("administratorId") int administratorId, ModelMap modelMap) {
		String view="administrators/listAdministrator";
		Optional<Administrator> administrator = administratorService.findAdministratorById(administratorId);
		if(administrator.isPresent()) {
			administratorService.delete(administrator.get());
			modelMap.addAttribute("message", "Administrator successfully deleted!");
			view=listAdministrators(modelMap);
		}else {
			modelMap.addAttribute("message", "Administrator not found!");
			view=listAdministrators(modelMap);
		}
		return view;
	}
}
