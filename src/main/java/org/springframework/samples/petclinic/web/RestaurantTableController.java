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

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/restaurantTables")
public class RestaurantTableController {
	@Autowired
	private RestaurantTableService RestaurantTableService;
	
	@Autowired
	private RestaurantTableValidator restaurantTableValidator;
	
	@InitBinder("restaurantTable")
	public void initRestaurantTableBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(restaurantTableValidator);
	}
	
	@GetMapping()
	public String restaurantTablesList(ModelMap modelMap) {
		log.info("Loading list of restaurant tables view");
		String view= "restaurantTables/restaurantTablesList";
		Iterable<RestaurantTable> restaurantTables=RestaurantTableService.findAll();
		modelMap.addAttribute("restaurantTables", restaurantTables);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createRestaurantTable(ModelMap modelMap) {
		log.info("Loading new restaurant table form");
		String view = "restaurantTables/addRestaurantTable";
		modelMap.addAttribute("restaurantTable", new RestaurantTable());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveRestaurantTable(@Valid RestaurantTable restaurantTable, BindingResult result, ModelMap modelMap) {
		log.info("Saving restaurant table: " + restaurantTable.getId());
		String view="restaurantTables/restaurantTablesList";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("restaurantTable", restaurantTable);
			return "restaurantTables/addRestaurantTable";
			
		}else {
			if(restaurantTableValidator.getRestaurantTablewithIdDifferent(restaurantTable)) {
				log.warn("Restaurant table duplicated:" + restaurantTable.getId());
				modelMap.addAttribute("restauranttable", restaurantTable);
				return "restauranttables/addRestaurantTable";
			}
			log.info("Restaurant table validated: saving into DB");
			RestaurantTableService.save(restaurantTable);
			modelMap.addAttribute("message", "RestaurantTable successfully saved!");
			view=restaurantTablesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{restaurantTableId}")
	public String deleteRestaurantTable(@PathVariable("restaurantTableId") int restaurantTableId, ModelMap modelMap) {
		log.info("Deleting restaurant table: " + restaurantTableId);
		String view="restaurantTables/restaurantTablesList";
		Optional<RestaurantTable> restaurantTable = RestaurantTableService.findRestaurantTableId(restaurantTableId);
		if(restaurantTable.isPresent()) {
			if(restaurantTableValidator.isUsedInRestaurantReservation(restaurantTable)) {
				log.warn("The restaurant table can't be deleted, it has reservations!");
				modelMap.addAttribute("message", "This RestaurantTable can't be deleted, it has reservations!");

			}else{
				log.info("Restaurant table found: deleting");
				RestaurantTableService.delete(restaurantTable.get());
				modelMap.addAttribute("message", "RestaurantTable successfully deleted!");
				view=restaurantTablesList(modelMap);
			}
		}else {
			log.warn("Restaurant reservation not found in DB: " + restaurantTableId);
			modelMap.addAttribute("message", "RestaurantTable not found!");
			view=restaurantTablesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{restaurantTableId}/edit")
	public String initUpdateRestaurantTableForm(@PathVariable("restaurantTableId") int restaurantTableId, ModelMap model) {
		log.info("Loading update restaurant table form");
		RestaurantTable restaurantTable = RestaurantTableService.findRestaurantTableId(restaurantTableId).get();
		model.put("restaurantTable", restaurantTable);
		return "restauranttables/updateRestaurantTable";
	}

	@PostMapping(value = "/{restaurantTableId}/edit")
	public String processUpdateRestaurantTableForm(@Valid RestaurantTable restaurantTable, BindingResult result,
			@PathVariable("restaurantTableId") int restaurantTableId, ModelMap model) {
		log.info("Updating restaurant table: " + restaurantTableId);
		restaurantTable.setId(restaurantTableId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("restaurantTable", restaurantTable);
			return "restaurantTables/updateRestaurantTable";
		}
		else {
			if(restaurantTableValidator.getRestaurantTablewithIdDifferent(restaurantTable, restaurantTable.getId())) {
				log.warn("Restaurant table duplicated");
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				model.put("restaurantTable", restaurantTable);
				return "restaurantTables/updateRestaurantTable";
			}
			log.info("Restaurant table validated: updating into DB");
			this.RestaurantTableService.save(restaurantTable);
			return "redirect:/restaurantTables";
		}
	}
	
	@ModelAttribute("waiters")
    public Collection<Waiter> populateWaiters() {
        return this.RestaurantTableService.findWaiters();
    }
}
