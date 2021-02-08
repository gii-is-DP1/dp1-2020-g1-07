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

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
		log.info("Loading list of Maintenance Workers");
		String view= "maintenanceWorkers/listMaintenanceWorker";
		Iterable<MaintenanceWorker> maintenanceWorkers=mworkerService.findAll();
		modelMap.addAttribute("maintenanceWorkers", maintenanceWorkers);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createMaintenanceWorker(ModelMap modelMap) {
		log.info("Loading new Maintenance Worker form");
		String view="maintenanceWorkers/addMaintenanceWorker";
		modelMap.addAttribute("maintenanceWorker", new MaintenanceWorker());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveMaintenanceWorker(@Valid MaintenanceWorker maintenanceWorker, BindingResult result, ModelMap modelMap) {
		log.info("Saving Maintenance Worker:" + maintenanceWorker.getId());
		String view="maintenanceWorkers/listMaintenanceWorker";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("maintenanceWorker", maintenanceWorker);
			return "maintenanceWorkers/addMaintenanceWorker";
			
		}else {
			if (validator.getMaintenanceWorkerwithIdDifferent(maintenanceWorker.getDni(), null)) {
				log.warn("Maintenance Worker with dni" + maintenanceWorker.getDni() + "already in database");
				result.rejectValue("dni", "dni.duplicate", "MaintenanceWorker with dni" + maintenanceWorker.getDni() + "already in database");
				modelMap.addAttribute("maintenanceWorker", maintenanceWorker);
				return "maintenanceWorkers/addMaintenanceWorker";
			}
			log.info("Maintenance Worker validated: saving into DB");
			mworkerService.save(maintenanceWorker);
			
			modelMap.addAttribute("message", "MaintenanceWorker successfully saved!");
			view=listMaintenanceWorkers(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{maintenanceWorkerId}")
	public String deleteMaintenanceWorker(@PathVariable("maintenanceWorkerId") int maintenanceWorkerId, ModelMap modelMap) {
		log.info("Deleting Maintenance Worker:" + maintenanceWorkerId);
		String view="maintenanceWorkers/listMaintenanceWorker";
		Optional<MaintenanceWorker> maintenanceWorker = mworkerService.findMaintenanceWorkerById(maintenanceWorkerId);
		if(maintenanceWorker.isPresent()) {
			log.info("Maintenance Worker found: deleting");
			mworkerService.delete(maintenanceWorker.get());
			modelMap.addAttribute("message", "MaintenanceWorker successfully deleted!");
			view=listMaintenanceWorkers(modelMap);
		}else {
			log.warn("Maintenance Worker not found in DB: "+ maintenanceWorkerId);
			modelMap.addAttribute("message", "MaintenanceWorker not found!");
			view=listMaintenanceWorkers(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{maintenanceWorkerId}/edit")
    public String initUpdateMaintenanceWorkerForm(@PathVariable("maintenanceWorkerId") int maintenanceWorkerId, ModelMap model) {
		log.info("Loading update Maintenance Worker form: " + maintenanceWorkerId);
		MaintenanceWorker maintenanceWorker = mworkerService.findMaintenanceWorkerById(maintenanceWorkerId).get();
        model.put("maintenanceWorker", maintenanceWorker);
        return "maintenanceWorkers/updateMaintenanceWorker";
    }

    @PostMapping(value = "/{maintenanceWorkerId}/edit")
    public String processUpdateMaintenanceWorkerForm(@Valid MaintenanceWorker maintenanceWorker, BindingResult result,
            @PathVariable("maintenanceWorkerId") int maintenanceWorkerId, ModelMap model) {
    	log.info("Updating Maintenance Worker: " + maintenanceWorkerId);
    	maintenanceWorker.setId(maintenanceWorkerId);
        if (result.hasErrors()) {
        	log.warn("Found errors on update: " + result.getAllErrors());
            model.put("maintenanceWorker", maintenanceWorker);
            return "maintenanceWorkers/updateMaintenanceWorker";
        }
        else {
        	if (validator.getMaintenanceWorkerwithIdDifferent(maintenanceWorker.getDni(), maintenanceWorker.getId())) {
        		log.warn("Maintenance Worker duplicated");
				result.rejectValue("dni", "dni.duplicate", "MaintenanceWorker with dni" + maintenanceWorker.getDni() + "already in database");
				model.addAttribute("maintenanceWorker", maintenanceWorker);
				return "maintenanceWorkers/updateMaintenanceWorker";
			}
        	log.info("Maintenance Worker validated: updating into DB");
            this.mworkerService.save(maintenanceWorker);
            return "redirect:/maintenanceWorkers";
        }
    }
}
