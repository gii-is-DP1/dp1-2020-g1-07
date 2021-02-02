package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.RestaurantTableService;
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

@Controller
@RequestMapping("/restauranttables")
public class RestaurantTableController {
	@Autowired
	private RestaurantTableService RestaurantTableService;
	
	@Autowired
	private RestaurantTableValidator restauranttableValidator;
	
	@InitBinder("restauranttable")
	public void initRestaurantTableBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(restauranttableValidator);
	}
	
	@GetMapping()
	public String restauranttablesList(ModelMap modelMap) {
		String view= "restauranttables/restauranttablesList";
		Iterable<RestaurantTable> restauranttables=RestaurantTableService.findAll();
		modelMap.addAttribute("restauranttables", restauranttables);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createRestaurantTable(ModelMap modelMap) {
		String view = "restauranttables/addRestaurantTable";
		modelMap.addAttribute("restaurantTable", new RestaurantTable());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveRestaurantTable(@Valid RestaurantTable restauranttable, BindingResult result, ModelMap modelMap) {
		String view="restauranttables/restauranttablesList";
		if(result.hasErrors()) {
			modelMap.addAttribute("restauranttable", restauranttable);
			return "restauranttables/addRestaurantTable";
			
		}else {
			if(restauranttableValidator.getRestaurantTablewithIdDifferent(restauranttable)) {
				modelMap.addAttribute("restauranttable", restauranttable);
				return "restauranttables/addRestaurantTable";
			}
			RestaurantTableService.save(restauranttable);
			modelMap.addAttribute("message", "RestaurantTable successfully saved!");
			view=restauranttablesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{restauranttableId}")
	public String deleteRestaurantTable(@PathVariable("restauranttableId") int restauranttableId, ModelMap modelMap) {
		String view="restauranttables/restauranttablesList";
		Optional<RestaurantTable> restauranttable = RestaurantTableService.findRestaurantTableId(restauranttableId);
		if(restauranttable.isPresent()) {
			if(restauranttableValidator.isUsedInRestaurantReservation(restauranttable)) {
				modelMap.addAttribute("message", "This RestaurantTable can't be deleted, it has reservations!");

			}else{
				RestaurantTableService.delete(restauranttable.get());
				modelMap.addAttribute("message", "RestaurantTable successfully deleted!");
				view=restauranttablesList(modelMap);
			}
		}else {
			modelMap.addAttribute("message", "RestaurantTable not found!");
			view=restauranttablesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{restauranttableId}/edit")
	public String initUpdateCasTbForm(@PathVariable("restauranttableId") int restauranttableId, ModelMap model) {
		RestaurantTable restauranttable = RestaurantTableService.findRestaurantTableId(restauranttableId).get();
		
		model.put("restauranttable", restauranttable);
		return "restauranttables/updateRestaurantTable";
	}

	@PostMapping(value = "/{restauranttableId}/edit")
	public String processUpdateCasTbForm(@Valid RestaurantTable restauranttable, BindingResult result,
			@PathVariable("restauranttableId") int restauranttableId, ModelMap model) {
		restauranttable.setId(restauranttableId);
		if (result.hasErrors()) {
			model.put("restauranttable", restauranttable);
			return "restauranttables/updateRestaurantTable";
		}
		else {
			if(restauranttableValidator.getRestaurantTablewithIdDifferent(restauranttable, restauranttable.getId())) {
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				model.put("restauranttable", restauranttable);
				return "restauranttables/updateRestaurantTable";
			}
			this.RestaurantTableService.save(restauranttable);
			return "redirect:/restauranttables";
		}
	}
	
	@ModelAttribute("waiters")
    public Collection<Waiter> populateWaiters() {
        return this.RestaurantTableService.findWaiters();
    }
}
