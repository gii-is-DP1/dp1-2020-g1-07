package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;

import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.EventService;
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
@RequestMapping("/events")
public class EventController {
		
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventValidator eventValidator;
	
	@InitBinder("event")
	public void initMenuBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(eventValidator);
	}
	
	@GetMapping()
	public String eventsList(ModelMap modelMap) {
		String view = "events/listEvent";
		Iterable<Event> events=eventService.findAll();
		modelMap.addAttribute("events", events);
		return view;
	}
	
	
	@GetMapping(path="/byDay")
	public String eventsByDay(ModelMap modelMap) {
		String vista= "events/eventsByDay";
		Collection<LocalDate> list=eventService.findAllDates();
		Iterable<LocalDate> dates = list;
		modelMap.addAttribute("dates", dates);
		return vista;
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/byDay/{date}", method = RequestMethod.GET)
	public String loadEventsByDate(@PathVariable("date")String datestr) {
		String json = "[";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		Stage scenario = null;
		try {
			List<Event> events = new ArrayList<Event>(eventService.findEventsByDate(date));
			for(Event event:events) {
				scenario = eventService.findStageForEvent(event.getId());
				json = json + "{\"id\":" + event.getId() +","
						+ "\"name\":\"" + event.getName() +"\","
						+ "\"showtype_id\":{"
								+ "\"id\":" + event.getShowtype_id().getId() + ","
								+ "\"name\":\"" + event.getShowtype_id().getName() + "\"},"
						+ "\"artist_id\":{"
								+ "\"id\":" + event.getArtist_id().getId() + ","
								+ "\"name\":\"" + event.getArtist_id().getName() + "\"},"
						+ "\"stage_id\":{"
								+ "\"id\":" + scenario.getId() + "},"
						+ "\"date\":\"" + event.getDate() +"\"},";
				if(events.indexOf(event)==events.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(events.size()==0) {
				json = json.substring(0, json.length() - 1) + "]";
			}
		
		Event evento = null;
		String nombre = evento.getName();
		}catch(Exception e) {
			System.out.println(json);
			System.out.println(scenario);
			System.out.println(e);
		}
		return json;
	}*/

	@GetMapping(path="/new")
	public String createEvent(ModelMap modelMap) {
		String view="events/addEvent";
		modelMap.addAttribute("event", new Event());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveEvent(@Valid Event event, BindingResult result, ModelMap modelMap) {
		String view="events/listEvent";
		if(result.hasErrors()) {
			modelMap.addAttribute("event", event);
			return "events/addEvent";
			
		}else {
			if(eventValidator.eventWithTheSameName(event.getName())){
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				modelMap.addAttribute("event", event);
				return "events/addEvent";
			}else {
			eventService.save(event);
			modelMap.addAttribute("message", "Event successfully saved!");
			view=eventsList(modelMap);
			}
		}
		return view;
	}
	@GetMapping(path="/delete/{eventId}")
	public String deleteEvent(@PathVariable("eventId") int eventId, ModelMap modelMap) {
		String view="events/listEvent";
		Optional<Event> event = eventService.findEventbyId(eventId);
		if(event.isPresent()) {
			eventService.delete(event.get());
			modelMap.addAttribute("message", "Event not found!");
			view=eventsList(modelMap);
		}
		return view;
	}
	@GetMapping(value = "/{eventId}/edit")
	public String initUpdategameForm(@PathVariable("eventId") int eventId, ModelMap model) {
		Event event = eventService.findEventbyId(eventId).get();
		
		model.put("event", event);
		return "events/updateEvent";
	}

	@PostMapping(value = "/{eventId}/edit")
	public String processUpdategameForm(@Valid Event event, BindingResult result,
			@PathVariable("eventId") int eventId, ModelMap model) {
		event.setId(eventId);
		if (result.hasErrors()) {
			model.put("event", event);
			return "events/updateEvent";
		}
		else {
			if(eventValidator.eventWithTheSameName_Update(event.getName(), eventId)){
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				model.addAttribute("event", event);
				return "events/updateEvent";
			}
			
			this.eventService.save(event);
			return "redirect:/events";
		}
	}
	


	@ModelAttribute("showtypes")
    public Collection<ShowType> populateShowtypes() {
        return this.eventService.findShowTypes();
    }
	@ModelAttribute("artists")
    public Collection<Artist> populateArtists() {
        return this.eventService.findArtists();
    }
	
	
}