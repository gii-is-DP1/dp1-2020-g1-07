package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.service.ArtistService;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.ScheduleService;
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
@RequestMapping("/artists")
public class ArtistController {
	
	private List<Event> actedEvents;
	private List<Integer> actedEventsId;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private ScheduleService scheService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ArtistValidator validator;
	
	@InitBinder("artist")
	public void initArtistBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listArtists(ModelMap modelMap) {
		log.info("Loading list of artists");
		String view= "artists/listArtist";
		Iterable<Artist> artists=artistService.findAll();
		modelMap.addAttribute("artists", artists);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createArtist(ModelMap modelMap) {
		log.info("Loading new artist form");
		String view="artists/addArtist";
		modelMap.addAttribute("artist", new Artist());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveArtist(@Valid Artist artist, BindingResult result, ModelMap modelMap) {
		log.info("Saving artist:" + artist.getId());
		String view="artists/listArtist";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("artist", artist);
			return "artists/addArtist";
			
		}else {
			if (validator.getArtistwithIdDifferent(artist.getDni(), null)) {
				log.warn("Artist with dni" + artist.getDni() + "already in database");
				result.rejectValue("dni", "dni.duplicate", "Artist with dni" + artist.getDni() + "already in database");
				modelMap.addAttribute("artist", artist);
				return "artists/addArtist";
			}
			log.info("Artist validated: saving into DB");
			artistService.save(artist);
			modelMap.addAttribute("message", "Artist successfully saved!");
			view=listArtists(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{artistId}")
	public String deleteArtist(@PathVariable("artistId") int artistId, ModelMap modelMap) {
		log.info("Deleting artist:" + artistId);
		String view="artists/listArtist";
		Optional<Artist> artist = artistService.findArtistById(artistId);
		if(artist.isPresent()) {
			log.info("Artist found: deleting");
			StreamSupport.stream(scheService.findAll().spliterator(), false)
			.filter(x -> x.getEmp().equals(artist.get()))
			.forEach(x -> scheService.delete(x));
			artistService.delete(artist.get());
			modelMap.addAttribute("message", "Artist successfully deleted!");
			view=listArtists(modelMap);
		}else {
			log.warn("Artist not found in DB: "+ artistId);
			modelMap.addAttribute("message", "Artist not found!");
			view=listArtists(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{artistId}/edit")
    public String initUpdateArtistForm(@PathVariable("artistId") int artistId, ModelMap model) {
		log.info("Loading update artist form: " + artistId);
		Artist artist = artistService.findArtistById(artistId).get();
        model.put("artist", artist);
        return "artists/updateArtist";
    }

    @PostMapping(value = "/{artistId}/edit")
    public String processUpdateArtistForm(@Valid Artist artist, BindingResult result,
            @PathVariable("artistId") int artistId, ModelMap model) {
    	log.info("Updating artist: " + artistId);
    	artist.setId(artistId);
        if (result.hasErrors()) {
        	log.warn("Found errors on update: " + result.getAllErrors());
            model.put("artist", artist);
            return "artists/updateArtist";
        }
        else {
        	if (validator.getArtistwithIdDifferent(artist.getDni(), artist.getId())) {
        		log.warn("Artist duplicated");
				result.rejectValue("dni", "dni.duplicate", "Artist with dni" + artist.getDni() + "already in database");
				model.addAttribute("artist", artist);
				return "artists/updateArtist";
			}
        	log.info("Artist validated: updating into DB");
            this.artistService.save(artist);
            return "redirect:/artists";
        }
    }
    
    //Parte de controlador para los eventos
    
    
    @GetMapping(path="/acts/{artistId}")
	public String artistActs(@PathVariable("artistId") int artistId, ModelMap modelMap) {
    	log.info("Loading acts of artists for artist: " + artistId);
		String view="artists/assignedEvents";
		Artist artist = artistService.findArtistById(artistId).get();
		modelMap.put("artist",artist);
		actedEvents = artistService.findActedEvents(artistId);
		actedEventsId = ObtainEventsIds(actedEvents);
		modelMap.put("actedEvents", actedEvents);
		return view;
	}
    
    @GetMapping(path="/acts/{artistId}/new")
	public String createAct(@PathVariable("artistId") int artistId, ModelMap modelMap) {
    	log.info("Creating an act for artist:" + artistId);
		String view="artists/addAct";
		modelMap.put("event", new Event());
		List<Event> events = artistService.findEvents();
		List<String> notActed = new ArrayList<String>();
		for(Event event:events) {
			if(!actedEventsId.contains(event.getId())) {
				notActed.add(event.getName());
			}
		}
		modelMap.put("eventsNames", notActed);
		return view;
	}
	
    public List<Integer> ObtainEventsIds(List<Event> events){
    	List<Integer> res = new ArrayList<Integer>();
    	for(Event event:events) {
    		res.add(event.getId());
    	}
    	return res;
    }
    
	@PostMapping(path="/acts/{artistId}/save")
	public String saveAct(@PathVariable("artistId") int artistId, Event event, BindingResult result, ModelMap modelMap) {
		log.info("Saving an act for artist:" + artistId);
		String view="artists/assignedEvents";
		if(result.hasErrors() || event.getName()==null) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("event", event);
			modelMap.addAttribute("message", "There is no event to add!");
			return "artists/addAct";
			
		}else {
			log.info("Act validated: saving into DB");
			event = eventService.findEventByName(event.getName()).get();
			Artist artist = artistService.findArtistById(artistId).get();
			Collection<Event> acted = artist.getActs();
			acted.add(event);
			artist.setActs(acted);
			artistService.save(artist);
			modelMap.addAttribute("message", "The artist will act at this event!");
			view=artistActs(artistId,modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/acts/{artistId}/delete/{eventId}")
	public String deleteAct(@PathVariable("artistId") int artistId, @PathVariable("eventId") int eventId, ModelMap modelMap) {
		log.info("Deleting acts with artist id:" + artistId);
		String view="artists/assignedEvents";
		Optional<Artist> artist = artistService.findArtistById(artistId);
		Optional<Event> event = eventService.findEventbyId(eventId);
		if(artist.isPresent() && event.isPresent()) {
			log.info("Act found: deleting");
			Collection<Event> acted = artist.get().getActs();
			acted.remove(event.get());
			artist.get().setActs(acted);
			artistService.save(artist.get());
			modelMap.addAttribute("message", "Act successfully deleted!");
			view=artistActs(artistId,modelMap);
		}else {
			log.warn("Act not found in DB: "+ artistId + eventId);
			modelMap.addAttribute("message", "Artist or event not found!");
			view=artistActs(artistId,modelMap);
		}
		return view;
	}

}
