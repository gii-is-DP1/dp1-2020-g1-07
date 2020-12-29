package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.service.AdministratorService;
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
@RequestMapping("/administrators")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private AdministratorValidator validator;
	
	@InitBinder("administrator")
	public void initAdministratorBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
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
			return "administrators/addAdministrator";
			
		}else {
			if (validator.getAdministratorwithIdDifferent(administrator.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Administrator with dni" + administrator.getDni() + "already in database");
				modelMap.addAttribute("administrator", administrator);
				return "administrators/addAdministrator";
			}
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
	
	@GetMapping(value = "/{administratorId}/edit")
    public String initUpdateAdministratorForm(@PathVariable("administratorId") int administratorId, ModelMap model) {
		Administrator administrator = administratorService.findAdministratorById(administratorId).get();
        model.put("administrator", administrator);
        return "administrators/updateAdministrator";
    }

    @PostMapping(value = "/{administratorId}/edit")
    public String processUpdateAdministratorForm(@Valid Administrator administrator, BindingResult result,
            @PathVariable("administratorId") int administratorId, ModelMap model) {
        if (result.hasErrors()) {
            model.put("administrator", administrator);
            return "administrators/updateAdministrator";
        }
        else {
        	if (validator.getAdministratorwithIdDifferent(administrator.getDni(), administrator.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Administrator with dni" + administrator.getDni() + "already in database");
				model.addAttribute("administrator", administrator);
				return "administrators/updateAdministrator";
			}
        	administrator.setId(administratorId);
            this.administratorService.save(administrator);
            return "redirect:/administrators";
        }
    }
}
