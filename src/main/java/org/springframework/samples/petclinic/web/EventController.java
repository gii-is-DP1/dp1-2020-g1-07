package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;
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

@Controller
@RequestMapping("/events")
public class EventController {
		
	@Autowired
	private EventService eventService;
	
	@InitBinder("event")
	public void initMenuBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new EventValidator());
	}
	
	@GetMapping()
	public String eventsList(ModelMap modelMap) {
		String view = "events/listEvent";
		Iterable<Event> events=eventService.findAll();
		modelMap.addAttribute("events", events);
		return view;
	}

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
			
			eventService.save(event);
			
			modelMap.addAttribute("message", "Event successfully saved!");
			view=eventsList(modelMap);
		}
		return view;
	}
	@GetMapping(path="/delete/{eventId}")
	public String deleteEvent(@PathVariable("eventId") int eventId, ModelMap modelMap) {
		String view="events/listEvent";
		Optional<Event> event = eventService.findEventbyId(eventId);
		if(event.isPresent()) {
			eventService.delete(event.get());
			modelMap.addAttribute("message", "Event successfully deleted!");
			view=eventsList(modelMap);
		}else {
			modelMap.addAttribute("message", "Event not found!");
			view=eventsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{eventId}/edit")
	public String initUpdateEventForm(@PathVariable("eventId") int eventId, ModelMap model) {
		Event event = eventService.findEventbyId (eventId).get();
		
		model.put("event", event);
		return "events/updateEvent";
	}
	
	@PostMapping(value = "/{eventId}/edit")
	public String processUpdateEventForm(@Valid Event event, BindingResult result,
			@PathVariable("eventId") int eventId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("event", event);
			return "events/updateEvent";
		}
		else {
			event.setId(eventId);
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
