package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.MaintenanceWorker;
import org.springframework.samples.petclinic.service.MaintenanceWorkerService;
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
@RequestMapping("/maintenanceWorkers")
public class MaintenanceWorkerController {
	
	@Autowired
	private MaintenanceWorkerService mworkerService;
	
	@Autowired
	private MaintenanceWorkerValidator validator;
	
	@InitBinder("maintenanceWorker")
	public void initMaintenanceWorkerBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listMaintenanceWorkers(ModelMap modelMap) {
		String view= "maintenanceWorkers/listMaintenanceWorker";
		Iterable<MaintenanceWorker> maintenanceWorkers=mworkerService.findAll();
		modelMap.addAttribute("maintenanceWorkers", maintenanceWorkers);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createMaintenanceWorker(ModelMap modelMap) {
		String view="maintenanceWorkers/addMaintenanceWorker";
		modelMap.addAttribute("maintenanceWorker", new MaintenanceWorker());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveMaintenanceWorker(@Valid MaintenanceWorker maintenanceWorker, BindingResult result, ModelMap modelMap) {
		String view="maintenanceWorkers/listMaintenanceWorker";
		if(result.hasErrors()) {
			modelMap.addAttribute("maintenanceWorker", maintenanceWorker);
			return "maintenanceWorkers/addMaintenanceWorker";
			
		}else {
			if (validator.getMaintenanceWorkerwithIdDifferent(maintenanceWorker.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "MaintenanceWorker with dni" + maintenanceWorker.getDni() + "already in database");
				modelMap.addAttribute("maintenanceWorker", maintenanceWorker);
				return "maintenanceWorkers/addMaintenanceWorker";
			}
			mworkerService.save(maintenanceWorker);
			
			modelMap.addAttribute("message", "MaintenanceWorker successfully saved!");
			view=listMaintenanceWorkers(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{maintenanceWorkerId}")
	public String deleteMaintenanceWorker(@PathVariable("maintenanceWorkerId") int maintenanceWorkerId, ModelMap modelMap) {
		String view="maintenanceWorkers/listMaintenanceWorker";
		Optional<MaintenanceWorker> maintenanceWorker = mworkerService.findMaintenanceWorkerById(maintenanceWorkerId);
		if(maintenanceWorker.isPresent()) {
			mworkerService.delete(maintenanceWorker.get());
			modelMap.addAttribute("message", "MaintenanceWorker successfully deleted!");
			view=listMaintenanceWorkers(modelMap);
		}else {
			modelMap.addAttribute("message", "MaintenanceWorker not found!");
			view=listMaintenanceWorkers(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{maintenanceWorkerId}/edit")
    public String initUpdateMaintenanceWorkerForm(@PathVariable("maintenanceWorkerId") int maintenanceWorkerId, ModelMap model) {
		MaintenanceWorker maintenanceWorker = mworkerService.findMaintenanceWorkerById(maintenanceWorkerId).get();
        model.put("maintenanceWorker", maintenanceWorker);
        return "maintenanceWorkers/updateMaintenanceWorker";
    }

    @PostMapping(value = "/{maintenanceWorkerId}/edit")
    public String processUpdateMaintenanceWorkerForm(@Valid MaintenanceWorker maintenanceWorker, BindingResult result,
            @PathVariable("maintenanceWorkerId") int maintenanceWorkerId, ModelMap model) {
        if (result.hasErrors()) {
            model.put("maintenanceWorker", maintenanceWorker);
            return "maintenanceWorkers/updateMaintenanceWorker";
        }
        else {
        	if (validator.getMaintenanceWorkerwithIdDifferent(maintenanceWorker.getDni(), maintenanceWorker.getId())) {
				result.rejectValue("dni", "dni.duplicate", "MaintenanceWorker with dni" + maintenanceWorker.getDni() + "already in database");
				model.addAttribute("maintenanceWorker", maintenanceWorker);
				return "maintenanceWorkers/updateMaintenanceWorker";
			}
        	maintenanceWorker.setId(maintenanceWorkerId);
            this.mworkerService.save(maintenanceWorker);
            return "redirect:/maintenanceWorkers";
        }
    }
}
