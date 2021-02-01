package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
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
@RequestMapping("/restaurantreservations")
public class RestaurantReservationController {

	@Autowired
	private RestaurantReservationService RestaurantReservationService;
	
	@Autowired
	private RestaurantReservationValidator restaurantreservationValidator;
	
	@InitBinder("restaurantreservation")
	public void initRestaurantReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(restaurantreservationValidator);
	}
	
	@GetMapping()
	public String restaurantreservationsList(ModelMap modelMap) {
		String view= "restaurantreservations/restaurantreservationsList";
		Iterable<RestaurantReservation> restaurantreservations=RestaurantReservationService.findAll();
		modelMap.addAttribute("restaurantreservations", restaurantreservations);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createRestaurantReservation(ModelMap modelMap) {
		String view="restaurantreservations/addRestaurantReservation";
		modelMap.addAttribute("restaurantreservation", new RestaurantReservation());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveRestaurantReservation(@Valid RestaurantReservation restaurantreservation, BindingResult result, ModelMap modelMap) {
		String view="restaurantreservations/restaurantreservationsList";
		if(result.hasErrors()) {
			modelMap.addAttribute("restaurantreservation", restaurantreservation);
			return "restaurantreservations/addRestaurantReservation";
			
		}else {
			if(restaurantreservationValidator.getRestaurantReservationwithIdDifferent(restaurantreservation)) {
				modelMap.addAttribute("restaurantreservation", restaurantreservation);
				return "restaurantreservations/addRestaurantReservation";
			}
			RestaurantReservationService.save(restaurantreservation);
			modelMap.addAttribute("message", "RestaurantReservation successfully saved!");
			view=restaurantreservationsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{restaurantreservationId}")
	public String deleteRestaurantReservation(@PathVariable("restaurantreservationId") int restaurantreservationId, ModelMap modelMap) {
		String view="restaurantreservations/restaurantreservationsList";
		Optional<RestaurantReservation> restaurantreservation = RestaurantReservationService.findRestaurantReservationId(restaurantreservationId);
		if(restaurantreservation.isPresent()) {
				RestaurantReservationService.delete(restaurantreservation.get());
				modelMap.addAttribute("message", "RestaurantReservation successfully deleted!");
				view=restaurantreservationsList(modelMap);
		}else {
			modelMap.addAttribute("message", "RestaurantReservation not found!");
			view=restaurantreservationsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{restaurantreservationId}/edit")
	public String initUpdateCasTbForm(@PathVariable("restaurantreservationId") int restaurantreservationId, ModelMap model) {
		RestaurantReservation restaurantreservation = RestaurantReservationService.findRestaurantReservationId(restaurantreservationId).get();
		
		model.put("restaurantreservation", restaurantreservation);
		return "restaurantreservations/updateRestaurantReservation";
	}

	@PostMapping(value = "/{restaurantreservationId}/edit")
	public String processUpdateCasTbForm(@Valid RestaurantReservation restaurantreservation, BindingResult result,
			@PathVariable("restaurantreservationId") int restaurantreservationId, ModelMap model) {
		restaurantreservation.setId(restaurantreservationId);
		if (result.hasErrors()) {
			model.put("restaurantreservation", restaurantreservation);
			return "restaurantreservations/updateRestaurantReservation";
		}
		else {
			if(restaurantreservationValidator.getRestaurantReservationwithIdDifferent(restaurantreservation, restaurantreservation.getId())) {
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				model.put("restaurantreservation", restaurantreservation);
				return "restaurantreservations/updateRestaurantReservation";
			}
			this.RestaurantReservationService.save(restaurantreservation);
			return "redirect:/restaurantreservations";
		}
	}
	
	@ModelAttribute("time_intervals")
	public Collection<TimeInterval> populateTimeIntervals() {
		return this.RestaurantReservationService.findTimeIntervals();
	}
}
