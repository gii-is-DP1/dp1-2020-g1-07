package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.WaiterService;
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
@RequestMapping("/waiters")
public class WaiterController {

	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private WaiterValidator validator;
	
	@InitBinder("waiter")
	public void initWaiterBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listWaiters(ModelMap modelMap) {
		String view= "waiters/listWaiter";
		Iterable<Waiter> waiters=waiterService.findAll();
		modelMap.addAttribute("waiters", waiters);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createWaiter(ModelMap modelMap) {
		String view="waiters/addWaiter";
		modelMap.addAttribute("waiter", new Waiter());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveWaiter(@Valid Waiter waiter, BindingResult result, ModelMap modelMap) {
		String view="waiters/listWaiter";
		if(result.hasErrors()) {
			modelMap.addAttribute("waiter", waiter);
			return "waiters/addWaiter";
			
		}else {
			if (validator.getWaiterwithIdDifferent(waiter.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Waiter with dni" + waiter.getDni() + "already in database");
				modelMap.addAttribute("waiter", waiter);
				return "waiters/addWaiter";
			}
			waiterService.save(waiter);
			
			modelMap.addAttribute("message", "Waiter successfully saved!");
			view=listWaiters(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{waiterId}")
	public String deleteWaiter(@PathVariable("waiterId") int waiterId, ModelMap modelMap) {
		String view="waiters/listWaiter";
		Optional<Waiter> waiter = waiterService.findWaiterById(waiterId);
		if(waiter.isPresent()) {
			waiterService.delete(waiter.get());
			modelMap.addAttribute("message", "Waiter successfully deleted!");
			view=listWaiters(modelMap);
		}else {
			modelMap.addAttribute("message", "Waiter not found!");
			view=listWaiters(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{waiterId}/edit")
    public String initUpdateWaiterForm(@PathVariable("waiterId") int waiterId, ModelMap model) {
		Waiter waiter = waiterService.findWaiterById(waiterId).get();
        model.put("waiter", waiter);
        return "waiters/updateWaiter";
    }

    @PostMapping(value = "/{waiterId}/edit")
    public String processUpdateWaiterForm(@Valid Waiter waiter, BindingResult result,
            @PathVariable("waiterId") int waiterId, ModelMap model) {
    	waiter.setId(waiterId);
        if (result.hasErrors()) {
            model.put("waiter", waiter);
            return "waiters/updateWaiter";
        }
        else {
        	if (validator.getWaiterwithIdDifferent(waiter.getDni(), waiter.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Waiter with dni" + waiter.getDni() + "already in database");
				model.addAttribute("waiter", waiter);
				return "waiters/updateWaiter";
			}
        	
            this.waiterService.save(waiter);
            return "redirect:/waiters";
        }
    }
}
