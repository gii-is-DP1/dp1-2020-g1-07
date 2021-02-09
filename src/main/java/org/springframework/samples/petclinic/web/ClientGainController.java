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
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.service.ClientGainService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.samples.petclinic.util.Week;
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
@RequestMapping("/cgains")
public class ClientGainController {
	
	@Autowired
	private ClientGainService cgainService;
	
	@Autowired
	private ClientGainValidator validator;
	
	@InitBinder("clientGain")
	public void initClientGainBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listClientGains(ModelMap modelMap) {
		log.info("Loading list of cgains view");
		String view= "cgains/listClientGain";
		Iterable<ClientGain> cgains=cgainService.findAll();
		modelMap.addAttribute("cgains", cgains);
		return view;
	}
	
	@GetMapping(path="/user")
	public String userGains(ModelMap modelMap) {
		log.info("Loading user cgains view");
		String view= "cgains/myGains";
		SortedSet<Week> weeks= cgainService.findWeeksForUser();
		Iterable<Week> dates = weeks;
		log.info("Weeks found: " + weeks.size());
		modelMap.addAttribute("dates", dates);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/{date}", method = RequestMethod.GET)
	public String loadUserGains(@PathVariable("date")String datestr) {
		log.info("Loading user gains for week starting at: " + datestr);
		String username = UserUtils.getUser();
		String dni = cgainService.findClientByUsername(username).getDni();
		log.info("User found: " + username + " --- " + dni);
		String json = "[";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<ClientGain> gains = new ArrayList<ClientGain>(cgainService.findClientGainsForWeek(new Week(date), dni));
			for(ClientGain cg : gains) {
				json = json + "{\"id\":" + cg.getId() +","
						+ "\"amount\":" + cg.getAmount() +","
						+ "\"date\":\"" + cg.getDate() +"\","
						+ "\"dni\":\"" + cg.getClient().getDni() +"\","
						+ "\"game\":\"" + cg.getGame().getName() +"\"},";
				if(gains.indexOf(cg)==gains.size()-1) {
					json = json.substring(0, json.length() - 1);
				}
			}
			json += "]";
			log.info("ClientGain JSON data: " + json);
		}catch(Exception e) {
			log.error("Error while parsing cgains data: " + e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	@ModelAttribute("clients")
	public Collection<Client> clients() {
		return this.cgainService.findClients();
	}
	
	@ModelAttribute("games")
	public Collection<Game> games() {
		return this.cgainService.findGames();
	}
	
	@GetMapping(path="/new")
	public String createClientGain(ModelMap modelMap) {
		log.info("Loading new cgain form");
		String view="cgains/addClientGain";
		modelMap.addAttribute("cgain", new ClientGain());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveClientGain(@Valid ClientGain cgain, BindingResult result, ModelMap modelMap) {
		log.info("Saving cgain: " + cgain.getId());
		String view="cgains/listClientGain";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("error", result.getFieldError().getDefaultMessage());
			modelMap.addAttribute("cgain", cgain);
			return "cgains/addClientGain";
			
		}else {
			log.info("Cgain validated: saving into DB");
			cgainService.save(cgain);
			modelMap.addAttribute("message", "ClientGain successfully saved!");
			view=listClientGains(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{cgainId}")
	public String deleteClientGain(@PathVariable("cgainId") int cgainId, ModelMap modelMap) {
		log.info("Deleting cgain: " + cgainId);
		String view="cgains/listClientGain";
		Optional<ClientGain> cgain = cgainService.findClientGainById(cgainId);
		if(cgain.isPresent()) {
			log.info("Cgain found: deleting");
			cgainService.delete(cgain.get());
			modelMap.addAttribute("message", "ClientGain successfully deleted!");
			view=listClientGains(modelMap);
		}else {
			log.warn("Cgain not found in DB: " + cgainId);
			modelMap.addAttribute("message", "ClientGain not found!");
			view=listClientGains(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{cgainId}/edit")
    public String initUpdateClientGainForm(@PathVariable("cgainId") int cgainId, ModelMap model) {
		log.info("Loading update cgain form");
		ClientGain cgain = cgainService.findClientGainById(cgainId).get();
        model.put("cgain", cgain);
        return "cgains/updateClientGain";
    }

    @PostMapping(value = "/{cgainId}/edit")
    public String processUpdateClientGainForm(@Valid ClientGain clientgain, BindingResult result,
            @PathVariable("cgainId") int cgainId, ModelMap model) {
    	log.info("Updating cgain: " + cgainId);
    	clientgain.setId(cgainId);
        if (result.hasErrors()) {
        	log.warn("Found errors on update: " + result.getAllErrors());
        	model.addAttribute("error", result.getFieldError().getDefaultMessage());
            model.put("cgain", clientgain);
            return "cgains/updateClientGain";
        }
        else {
        	log.info("Cgain validated: updating into DB");
            this.cgainService.save(clientgain);
            return "redirect:/cgains";
        }
    }
}
