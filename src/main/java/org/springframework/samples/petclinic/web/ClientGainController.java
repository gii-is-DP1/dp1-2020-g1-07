package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.service.ClientGainService;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.samples.petclinic.util.Week;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping()
	public String listClientGains(ModelMap modelMap) {
		String view= "cgains/listClientGain";
		Iterable<ClientGain> cgains=cgainService.findAll();
		modelMap.addAttribute("cgains", cgains);
		return view;
	}
	
	@GetMapping(path="/user")
	public String userGains(ModelMap modelMap) {
		log.info("Loading cgains/user page");
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
		log.info("Searching dni for username " + username);
		String dni = cgainService.findClientByUsername(username);
		log.info("Searching data for user with dni: " + dni);
		String json = "[";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<ClientGain> gains = new ArrayList<ClientGain>(cgainService.findClientGainsForWeek(new Week(date), dni));
			for(ClientGain cg : gains) {
				json = json + "{\"id\":" + cg.getId() +","
						+ "\"amount\":" + cg.getAmount() +","
						+ "\"date\":\"" + cg.getDate() +"\","
						+ "\"dni\":\"" + cg.getDni() +"\","
						+ "\"game\":\"" + cg.getGame() +"\"},";
				if(gains.indexOf(cg)==gains.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(gains.size()==0) {
				json = "[]";
			}
			log.info("ClientGain JSON data: " + json);
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	@ModelAttribute("clients_dnis")
	public Collection<String> clients() {
		return this.cgainService.findClients();
	}
	
	@ModelAttribute("games")
	public Collection<String> games() {
		return this.cgainService.findGames();
	}
	
	@GetMapping(path="/new")
	public String createClientGain(ModelMap modelMap) {
		String view="cgains/addClientGain";
		modelMap.addAttribute("cgain", new ClientGain());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveClientGain(@Valid ClientGain cgain, BindingResult result, ModelMap modelMap) {
		String view="cgains/listClientGain";
		if(result.hasErrors()) {
			modelMap.addAttribute("cgain", cgain);
			return "cgains/addClientGain";
			
		}else {
			
			cgainService.save(cgain);
			
			modelMap.addAttribute("message", "ClientGain successfully saved!");
			view=listClientGains(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{cgainId}")
	public String deleteClientGain(@PathVariable("cgainId") int cgainId, ModelMap modelMap) {
		String view="cgains/listClientGain";
		Optional<ClientGain> cgain = cgainService.findClientGainById(cgainId);
		if(cgain.isPresent()) {
			cgainService.delete(cgain.get());
			modelMap.addAttribute("message", "ClientGain successfully deleted!");
			view=listClientGains(modelMap);
		}else {
			modelMap.addAttribute("message", "ClientGain not found!");
			view=listClientGains(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{cgainId}/edit")
    public String initUpdateClientGainForm(@PathVariable("cgainId") int cgainId, ModelMap model) {
		ClientGain cgain = cgainService.findClientGainById(cgainId).get();
        model.put("cgain", cgain);
        return "cgains/updateClientGain";
    }

    @PostMapping(value = "/{cgainId}/edit")
    public String processUpdateClientGainForm(@Valid ClientGain clientgain, BindingResult result,
            @PathVariable("cgainId") int cgainId, ModelMap model) {
        if (result.hasErrors()) {
            model.put("cgain", clientgain);
            return "cgains/updateClientGain";
        }
        else {
        	clientgain.setId(cgainId);
            this.cgainService.save(clientgain);
            return "redirect:/cgains";
        }
    }
}
