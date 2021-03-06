package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ShowReservationService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/showress")
public class ShowReservationController {

	@Autowired
	private ShowReservationService showresService;
	
	@Autowired
	private ClientService clService;
	
	@Autowired
	private ShowReservationValidator validator;
	
	@InitBinder("showReservation")
	public void initShowReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@ModelAttribute("events")
    public Collection<Event> populateEvents() {
        return this.showresService.findAvailableShows();
    }
	
	@GetMapping()
	public String listShowReservations(ModelMap modelMap) {
		log.info("Loading list of showress view");
		String view= "showress/listShowReservation";
		Iterable<ShowReservation> showress=showresService.findAll();
		modelMap.addAttribute("showress", showress);
		return view;
	}
	
	@GetMapping(path="/user")
	public String userShowReservations(ModelMap modelMap) {
		log.info("Loading user showress view");
		String view= "showress/myShowReservations";
		String username = UserUtils.getUser();
		Client client = showresService.findClientFromUsername(username);
		Iterable<ShowReservation> showress = showresService.findReservationsForUser(client);
		modelMap.addAttribute("showress", showress);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createShowReservation(ModelMap modelMap) {
		log.info("Loading new showres form");
		String view="showress/addShowReservation";
		modelMap.addAttribute("showReservation", new ShowReservation());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveShowReservation(@Valid ShowReservation showres, BindingResult result, ModelMap modelMap) {
		log.info("Saving showres: " + showres.getId());
		String view="showress/listShowReservation";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("showReservation", showres);
			return "showress/addShowReservation";
			
		}else {
			log.info("Showres validated: saving into DB");
			String username = UserUtils.getUser();
			Client client = showresService.findClientFromUsername(username);
			showres.setClient(client);
			showresService.save(showres);
			modelMap.addAttribute("message", "ShowReservation successfully saved!");
			view=userShowReservations(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{showresId}")
	public String deleteShowReservation(@PathVariable("showresId") int showresId, ModelMap modelMap) {
		log.info("Deleting showres: " + showresId);
		Optional<ShowReservation> showres = showresService.findShowReservationById(showresId);
		if(showres.isPresent()) {
			log.info("Showres found: deleting");
			showresService.delete(showres.get());
			modelMap.addAttribute("message", "ShowReservation successfully deleted!");
		}else {
			log.warn("Showres not found in DB: " + showresId);
			modelMap.addAttribute("message", "ShowReservation not found!");
		}
		String view=listShowReservations(modelMap);
		if (showresService.findClientFromUsername(UserUtils.getUser()) != null)
			view=userShowReservations(modelMap);
		return view;
	}
	
	@GetMapping(value = "/{showresId}/edit/{clientId}")
    public String initUpdateShowReservationForm(@PathVariable("showresId") int showresId,
    		@PathVariable("clientId") int clientId,ModelMap model) {
		log.info("Loading update showres form");
		ShowReservation showres = showresService.findShowReservationById(showresId).get();
        model.put("showReservation", showres);  //OJO con la droga
        model.put("clientId", clientId);
        return "showress/updateShowReservation";
    }

    @PostMapping(value = "/{showresId}/edit/{clientId}")
    public String processUpdateShowReservationForm(@Valid ShowReservation showres, BindingResult result,
            @PathVariable("showresId") int showresId, @PathVariable("clientId") int clientId, ModelMap model) {
    	log.info("Updating showres: " + showresId);
    	showres.setId(showresId);
        if (result.hasErrors()) {
        	log.warn("Found errors on update: " + result.getAllErrors());
        	model.put("showReservation", showres);
            return "showress/updateShowReservation";
        }
        else {
        	log.info("Showres validated: updating into DB");
        	showres.setClient(clService.findClientById(clientId).get());
            this.showresService.save(showres);
            String view=listShowReservations(model);
    		if (showresService.findClientFromUsername(UserUtils.getUser()) != null)
    			view=userShowReservations(model);
            return "redirect:/" + view;
        }
    }
    
}
