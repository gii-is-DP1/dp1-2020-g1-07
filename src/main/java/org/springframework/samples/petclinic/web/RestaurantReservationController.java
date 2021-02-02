package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.RestaurantTableService;
import org.springframework.samples.petclinic.util.UserUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restaurantreservations")
public class RestaurantReservationController {

	@Autowired
	private RestaurantReservationService RestaurantReservationService;
	
	@Autowired
	private RestaurantTableService restaurantTableService;
	
	@Autowired
	private RestaurantReservationValidator restaurantreservationValidator;
	
	@InitBinder("restaurantreservation")
	public void initRestaurantReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(restaurantreservationValidator);
	}
	
	@GetMapping()
	public String restaurantreservationsList(ModelMap modelMap) {
		String view= "restaurantreservations/restaurantreservationsList";
		Collection<LocalDate> list=RestaurantReservationService.findAllDates();
		Iterable<LocalDate> dates = list;
		modelMap.addAttribute("dates", dates);
		Iterable<RestaurantReservation> restaurantreservations=RestaurantReservationService.findAll();
		modelMap.addAttribute("restaurantreservations", restaurantreservations);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{date}", method = RequestMethod.GET)
	public String loadRestaurantReservationsByDate(@PathVariable("date")String datestr) {
		String json = "[";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<RestaurantReservation> restaurantReservations = new ArrayList<RestaurantReservation>(RestaurantReservationService.findRestaurantReservationsByDate(date));
			for(RestaurantReservation restaurantReservation:restaurantReservations) {
				json = json + "{\"id\":" + restaurantReservation.getId() +","
						+ "\"date\":\"" + restaurantReservation.getDate() +"\","
						+ "\"restaurant_table\":{"
							+ "\"id\":" + restaurantReservation.getRestauranttable().getId() +"},"
						+ "\"client\":{"
							+ "\"id\":" + restaurantReservation.getClient().getId() +","
							+ "\"name\":\"" + restaurantReservation.getClient().getName() +"\"},"
						+ "\"time_interval\":{"
							+ "\"id\":" + restaurantReservation.getTimeInterval().getId() +","
							+ "\"name\":\"" + restaurantReservation.getTimeInterval().getName() +"\"}},";
				if(restaurantReservations.indexOf(restaurantReservation)==restaurantReservations.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(restaurantReservations.size()==0) {
				json = json.substring(0, json.length() - 1) + "]";
			}
		}catch(Exception e) {
			System.out.println(RestaurantReservationService.findRestaurantReservationsByDate(date));
		}
		return json;
	}
	
	@GetMapping(path="/new")
	public String createRestaurantReservation(ModelMap modelMap) {
		String view="restaurantreservations/addRestaurantReservation";
		RestaurantReservation reservation = new RestaurantReservation();
		String username = UserUtils.getUser();
		Client client = RestaurantReservationService.findClientFromUsername(username).get();
		reservation.setClient(client);
		modelMap.addAttribute("restaurantreservation", reservation);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/new/loadDinersByTimeInterval/{id}/{date}", method = RequestMethod.GET)
	public String loadDinersByTimeInterval(@PathVariable("date")String datestr, @PathVariable("id")int id) {
		String json = "[";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<RestaurantTable> tables = TablesbyDateAndTimeInterval(date,id);
			for(RestaurantTable table:tables) {
				json = json + "{\"id\":" + table.getId() +","
						+ "\"size\":" + table.getSize() +"},";
				if(tables.indexOf(table)==tables.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(tables.size()==0) {
				json = json + "]";
			}
		}catch(Exception e) {
			System.out.println(TablesbyDateAndTimeInterval(date,id));
		}
		return json;
	}
	
	private List<RestaurantTable> TablesbyDateAndTimeInterval(LocalDate date, int id) {
		// TODO Auto-generated method stub
		List<RestaurantTable> tables = StreamSupport.stream(restaurantTableService.findAll().spliterator(), false).collect(Collectors.toList());
		List<RestaurantTable> result = new ArrayList<RestaurantTable>();
		List<RestaurantReservation> reservations = new ArrayList<RestaurantReservation>(RestaurantReservationService.findRestaurantReservationsByDate(date));
		for(RestaurantReservation reservation:reservations) {
			if(reservation.getTimeInterval().getId()==id) {
				tables.remove(reservation.getRestauranttable());
			}
		}
		Set<Integer> sizes = new HashSet<Integer>();
		for(RestaurantTable table:tables) {
			if(!sizes.contains(table.getSize())) result.add(table);
		}
		return result;
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
	
	@ModelAttribute("restaurant_tables")
	public Collection<RestaurantTable> populateRestaurantTables() {
		return this.RestaurantReservationService.findRestaurantTables();
	}
}
