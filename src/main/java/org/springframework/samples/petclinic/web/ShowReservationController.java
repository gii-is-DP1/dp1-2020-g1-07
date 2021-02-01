package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.service.ShowReservationService;
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
@RequestMapping("/showres")
public class ShowReservationController {

	@Autowired
	private ShowReservationService showresService;
	
	@Autowired
	private ShowReservationValidator validator;
	
	@InitBinder("clientGain")
	public void initShowReservationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
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
	public String userGains(ModelMap modelMap) {
		return "";
	}
	
	@GetMapping(path="/new")
	public String createShowReservation(ModelMap modelMap) {
		log.info("Loading new showres form");
		String view="showress/addShowReservation";
		modelMap.addAttribute("showres", new ShowReservation());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveShowReservation(@Valid ShowReservation showres, BindingResult result, ModelMap modelMap) {
		log.info("Saving showres: " + showres.getId());
		String view="showress/listShowReservation";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("showres", showres);
			return "showress/addShowReservation";
			
		}else {
			log.info("Showres validated: saving into DB");
			showresService.save(showres);
			modelMap.addAttribute("message", "ShowReservation successfully saved!");
			view=listShowReservations(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{showresId}")
	public String deleteShowReservation(@PathVariable("showresId") int showresId, ModelMap modelMap) {
		log.info("Deleting showres: " + showresId);
		String view="showress/listShowReservation";
		Optional<ShowReservation> showres = showresService.findShowReservationById(showresId);
		if(showres.isPresent()) {
			log.info("Showres found: deleting");
			showresService.delete(showres.get());
			modelMap.addAttribute("message", "ShowReservation successfully deleted!");
			view=listShowReservations(modelMap);
		}else {
			log.warn("Showres not found in DB: " + showresId);
			modelMap.addAttribute("message", "ShowReservation not found!");
			view=listShowReservations(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{showresId}/edit")
    public String initUpdateShowReservationForm(@PathVariable("showresId") int showresId, ModelMap model) {
		log.info("Loading update showres form");
		ShowReservation showres = showresService.findShowReservationById(showresId).get();
        model.put("showres", showres);
        return "showress/updateShowReservation";
    }

    @PostMapping(value = "/{showresId}/edit")
    public String processUpdateShowReservationForm(@Valid ShowReservation showreservation, BindingResult result,
            @PathVariable("showresId") int showresId, ModelMap model) {
    	log.info("Updating showres: " + showresId);
    	showreservation.setId(showresId);
        if (result.hasErrors()) {
        	log.warn("Found errors on update: " + result.getAllErrors());
            model.put("showres", showreservation);
            return "showress/updateShowReservation";
        }
        else {
        	log.info("Showres validated: updating into DB");
            this.showresService.save(showreservation);
            return "redirect:/showress";
        }
    }
}
