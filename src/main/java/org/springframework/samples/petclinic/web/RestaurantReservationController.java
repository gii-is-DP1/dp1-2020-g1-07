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
import org.springframework.samples.petclinic.model.Authority;
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

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/restaurantreservations")
public class RestaurantReservationController {

	@Autowired
	private RestaurantReservationService RestaurantReservationService;
	
	@Autowired
	private RestaurantTableService restaurantTableService;
	
	@Autowired
	private RestaurantReservationValidator restaurantReservationValidator;
	
	@InitBinder("restaurantReservation")
	public void initRestaurantReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(restaurantReservationValidator);
	}
	
	private List<RestaurantReservation> clientreservations;
	private List<Integer> clientreservationsId;
	private Client editedClient;
	
	@GetMapping()
	public String restaurantreservationsList(ModelMap modelMap) {
		String username = UserUtils.getUser();
		Authority autority = RestaurantReservationService.getAuthority(username);
		List<LocalDate> list= new ArrayList<LocalDate>(RestaurantReservationService.findAllDates());
		List<RestaurantReservation> restaurantreservations= StreamSupport.stream(RestaurantReservationService.findAll().spliterator(), false).collect(Collectors.toList());
		log.info("Loading list of restaurant reservations: "+ restaurantreservations +" | with username: " + username + "");
		if(autority.getAuthority().equals("client")) {
			log.info("The user is a client");
			Client client = RestaurantReservationService.findClientFromUsername(username);
			clientreservations = new ArrayList<RestaurantReservation>();
			clientreservationsId = new ArrayList<Integer>();
			list.clear();
			for(RestaurantReservation restaurantreservation:restaurantreservations) {
				if(restaurantreservation.getClient().getDni().equals(client.getDni()) 
						&& !list.contains(restaurantreservation.getDate())) list.add(restaurantreservation.getDate());
				if(restaurantreservation.getClient().getDni().equals(client.getDni())) {
					clientreservations.add(restaurantreservation);
					clientreservationsId.add(restaurantreservation.getId());
				}
			}
			restaurantreservations = new ArrayList<RestaurantReservation>(clientreservations);
		}else {
			log.info("The user isn't a client");
			clientreservationsId = new ArrayList<Integer>();
			for(RestaurantReservation restaurantreservation:restaurantreservations) {
				clientreservationsId.add(restaurantreservation.getId());
			}
		}
		String view= "restaurantreservations/restaurantreservationsList";
		Iterable<LocalDate> dates = list;
		modelMap.addAttribute("dates", dates);
		modelMap.addAttribute("restaurantreservations", restaurantreservations);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/{date}", method = RequestMethod.GET)
	public String loadRestaurantReservationsByDate(@PathVariable("date")String datestr) {
		log.info("Loading restaurant reservations for the date: " + datestr);
		String json = "[";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<RestaurantReservation> restaurantReservations = new ArrayList<RestaurantReservation>(RestaurantReservationService.findRestaurantReservationsByDate(date));
			for(RestaurantReservation restaurantReservation:restaurantReservations) {
				if(clientreservationsId.contains(restaurantReservation.getId())) {
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
				}
				if(restaurantReservations.indexOf(restaurantReservation)==restaurantReservations.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(restaurantReservations.size()==0) {
				json = json + "]";
			}
		}catch(Exception e) {
			System.out.println(RestaurantReservationService.findRestaurantReservationsByDate(date));
		}
		log.info("The json of restaurant reservations is:" + json);
		return json;
	}
	
	@GetMapping(path="/new")
	public String createRestaurantReservation(ModelMap modelMap) {
		log.info("Loading new restaurant reservation form");
		String view="restaurantreservations/addRestaurantReservation";
		modelMap.addAttribute("restaurantReservation", new RestaurantReservation() );
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/new/loadDinersByTimeInterval/{id}/{date}", method = RequestMethod.GET)
	public String loadDinersByTimeInterval(@PathVariable("date")String datestr, @PathVariable("id")int id) {
		log.info("Loading diners for the time interval " + datestr + "and the id " + id );
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
		log.info("THe json of diners is: " + json);
		return json;
	}
	
	private List<RestaurantTable> TablesbyDateAndTimeInterval(LocalDate date, int id) {
		log.info("Loading the tables with the date " + date + "and the id" + id);
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
			if(!sizes.contains(table.getSize())) {
				sizes.add(table.getSize());
				result.add(table);
			}
		}
		return result;
	}

	@PostMapping(path="/save")
	public String saveRestaurantReservation(@Valid RestaurantReservation restaurantreservation, BindingResult result, ModelMap modelMap) {
		log.info("Saving restaurant reservation: " + restaurantreservation.getId());
		String view="restaurantreservations/restaurantreservationsList";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("restaurantReservation", restaurantreservation);
			return "restaurantreservations/addRestaurantReservation";
		}else {
			log.info("Restaurant reservation validated: saving into DB");
			RestaurantReservationService.save(restaurantreservation);
			modelMap.addAttribute("message", "RestaurantReservation successfully saved!");
			view=restaurantreservationsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{restaurantReservationId}")
	public String deleteRestaurantReservation(@PathVariable("restaurantReservationId") int restaurantreservationId, ModelMap modelMap) {
		log.info("Deleting restaurant reservation: " + restaurantreservationId);
		String view="restaurantreservations/restaurantreservationsList";
		Optional<RestaurantReservation> restaurantreservation = RestaurantReservationService.findRestaurantReservationId(restaurantreservationId);
		if(restaurantreservation.isPresent()) {
				log.info("Restaurant reservation found: deleting");
				RestaurantReservationService.delete(restaurantreservation.get());
				modelMap.addAttribute("message", "RestaurantReservation successfully deleted!");
				view=restaurantreservationsList(modelMap);
		}else {
			log.warn("Restaurant reservation not found in DB: " + restaurantreservationId);
			modelMap.addAttribute("message", "RestaurantReservation not found!");
			view=restaurantreservationsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{restaurantReservationId}/edit")
	public String initUpdateRestaurantReservationForm(@PathVariable("restaurantReservationId") int restaurantreservationId, ModelMap model) {
		log.info("Loading update restaurant reservation form");
		RestaurantReservation restaurantreservation = RestaurantReservationService.findRestaurantReservationId(restaurantreservationId).get();
		editedClient = restaurantreservation.getClient();
		model.put("client", editedClient);
		model.put("restaurantReservation", restaurantreservation);
		return "restaurantreservations/updateRestaurantReservation";
	}

	@PostMapping(value = "/{restaurantReservationId}/edit")
	public String processUpdateRsstaurantReservationForm(@Valid RestaurantReservation restaurantreservation, BindingResult result,
			@PathVariable("restaurantReservationId") int restaurantreservationId, ModelMap model) {
		log.info("Updating restaurant reservation: " + restaurantreservationId);
		restaurantreservation.setId(restaurantreservationId);
		restaurantreservation.setClient(editedClient);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("restaurantReservation", restaurantreservation);
			return "restaurantreservations/updateRestaurantReservation";
		}
		else {
			log.info("Restaurant reservation validated: updating into DB");
			this.RestaurantReservationService.save(restaurantreservation);
			return "redirect:/restaurantreservations";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/{restaurantReservationId}/edit/loadDinersByTimeInterval/{id}/{date}", method = RequestMethod.GET)
	public String loadDinersByTimeInterval(@PathVariable("date")String datestr, @PathVariable("id")int id, @PathVariable("restaurantReservationId")int reservationId ) {
		log.info("Loading the tables with the date " + datestr + ", the id" + id +" and the restaurant reservation " + reservationId );
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
		log.info("The json of diners is: " + json);
		return json;
	}
	
	@ModelAttribute("time_intervals")
	public Collection<TimeInterval> populateTimeIntervals() {
		return this.RestaurantReservationService.findTimeIntervals();
	}
	
	@ModelAttribute("restaurant_tables")
	public Collection<RestaurantTable> populateRestaurantTables() {
		return this.RestaurantReservationService.findRestaurantTables();
	}
	
	@ModelAttribute("client")
	public Client populateClient() {
		String username = UserUtils.getUser();
		Client client = RestaurantReservationService.findClientFromUsername(username);
		return client;
	}
}
