package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.service.CasinotableService;
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
@RequestMapping("/casinotables")
public class CasinotableController {

	 
	@Autowired
	private CasinotableService castableService;
	@Autowired
	private CasinotableValidator casinotabValidator;
	
	@InitBinder("casinotable")
	public void initScheduleBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(casinotabValidator);
	}
	
	@GetMapping()
	public String casinotablesListed(ModelMap modelMap) {
		log.info("Loading list of casinotables");
		String view = "casinotables/listCasinotable";
		Iterable<Casinotable> casinotables=castableService.findAll();
		modelMap.addAttribute("casinotables", casinotables);
		return view;
	}
	
	@GetMapping(path="/index")
	public String indexCasinotable(ModelMap modelMap) {
		log.info("Loading index");
		String view="casinotables/indexCasinotable";
		return view;
	}
	
	
	@GetMapping(path="/new")
	public String createCasinotable(ModelMap modelMap) {
		log.info("Loading new casinotable form");
		String view="casinotables/addCasinotable";
		modelMap.addAttribute("casinotable", new Casinotable());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveCasinotable(@Valid Casinotable casinotable, BindingResult result, ModelMap modelMap) throws ParseException {
		log.info("Saving casinotable:" + casinotable.getId());
		String view="casinotables/listCasinotable";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			if(result.getFieldError("gametype")!=null) modelMap.addAttribute("error",result.getFieldError("gametype").getDefaultMessage());
			modelMap.addAttribute("casinotable", casinotable);
			return "casinotables/addCasinotable";
		}else {
			if(casinotabValidator.getReverseDate(casinotable.getStartTime(), casinotable.getEndingTime())==true){
				log.warn("Found a error: the start time must be before the end time");
				result.rejectValue("startTime","", "The start time must be before the end time.");
				modelMap.addAttribute("casinotable", casinotable);
				return "casinotables/addCasinotable";
			}
			log.info("Casinotable validated: saving into DB");
			castableService.save(casinotable);
			modelMap.addAttribute("message", "Casinotable successfully saved!");
			view=casinotablesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{casinotableId}")
	public String deleteCasinoTable(@PathVariable("casinotableId") int casinotableId, ModelMap modelMap) {
		log.info("Deleting casinotable:" + casinotableId);
		String view="casinotables/listCasinotable";
		Optional<Casinotable> casinotable = castableService.findCasinotableById(casinotableId);
		if(casinotable.isPresent()) {
			log.info("Casinotable found: deleting");
			castableService.delete(casinotable.get());
			modelMap.addAttribute("message", "Casinotable successfully deleted!");
			view=casinotablesListed(modelMap);
		}else {
			log.warn("Casinotable not found in DB: "+ casinotableId);
			modelMap.addAttribute("message", "Casinotable not found!");
			view=casinotablesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{casinotableId}/edit")
	public String initUpdateCasTbForm(@PathVariable("casinotableId") int casinotableId, ModelMap model) {
		log.info("Loading update casinotable form: " + casinotableId);
		Casinotable casinotable = castableService.findCasinotableById(casinotableId).get();
		model.put("casinotable", casinotable);
		return "casinotables/updateCasinotable";
	}

	@PostMapping(value = "/{casinotableId}/edit")
	public String processUpdateCasTbForm(@Valid Casinotable casinotable, BindingResult result,
			@PathVariable("casinotableId") int casinotableId, ModelMap model) throws ParseException {
		log.info("Updating casinotable: " + casinotableId);
		casinotable.setId(casinotableId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			if(result.getFieldError("gametype")!=null) model.addAttribute("error",result.getFieldError("gametype").getDefaultMessage());
			model.put("casinotable", casinotable);
			return "casinotables/updateCasinotable";
		}
		else {
			if(casinotabValidator.getReverseDate(casinotable.getStartTime(), casinotable.getEndingTime())==true){
				log.warn("Found a error: the start time must be before the end time");
				result.rejectValue("startTime","", "The start time must be before the end time.");
				model.put("casinotable", casinotable);
				return "casinotables/updateCasinotable";
			}
			log.info("Casinotable validated: updating into DB");
			this.castableService.save(casinotable);
			return "redirect:/casinotables";
			 /*Optional<Casinotable> casinotableToUpdate=this.castableService.findCasinotableById(casinotableId);
			 Casinotable casinotableToUpdateGet = casinotableToUpdate.get();                                                                            
	                                      
	              this.castableService.save(casinotableToUpdateGet);                    
	                    
	            return "redirect:/casinotables/";*/
		}
	}
	
	@ResponseBody
    @RequestMapping(value = "/new/loadGamesByGameType/{id}", method = RequestMethod.GET)
    public String loadGamesByGameType(@PathVariable("id")int id) {
		log.info("Loading games by a gametype id:" + id);
        String json = "[";
        try {
            List<Game> games = new ArrayList<Game>(castableService.findGamesByGameType(id));
            for(Game game:games) {
                json = json + "{\"id\":" + game.getId() +","
                        + "\"name\":\"" + game.getName() +"\","
                        +"\"maxPlayers\":" + game.getMaxPlayers() +","
                        + "\"gametype\":{"
                            + "\"id\":" + game.getGametype().getId() + ","
                            + "\"name\":\"" + game.getGametype().getName() + "\"}},";

                if(games.indexOf(game)==games.size()-1) {
                    json = json.substring(0, json.length() - 1) + "]";
                }
            }
            
            if(games.size()==0) {
                json = json.substring(0, json.length() - 1) + "]";
            }
            
            log.info("Games JSON data: " + json);
        }catch(Exception e) {
        	log.error(e.getMessage());
            //System.out.println(menuService.findFirstDishesByShift(id));
        }
        
        return json;
    }

	@ModelAttribute("gametypes")
    public Collection<GameType> populateGameTypes() {
        return this.castableService.findGameTypes();
    }
	
	@ModelAttribute("skills")
    public Collection<Skill> populateSkills() {
        return this.castableService.findSkills();
    }
	@ModelAttribute("games")
    public Collection<Game> populateGames() {
        return this.castableService.findGames();
    }

	@ModelAttribute("clientgains")
	public String populateClientGain() {
		String json = "[";
		try {
			List<ClientGain> clientGainsRaw = new ArrayList<ClientGain>(castableService.findGains());
			List<ClientGain> clientGains = SumUpGainsByDate(clientGainsRaw);
			for(ClientGain clientGain:clientGains) {
				json = json + "{\"id\":" + clientGain.getId() +","
						+ "\"date\":\"" + clientGain.getDate() +"\","
						+ "\"amount\":" + clientGain.getAmount() +","
						+ "\"tableId\":" + clientGain.getTableId() +"},";
				if(clientGains.indexOf(clientGain)==clientGains.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(clientGains.size()==0) {
				json = json + "]";
			}
		}catch(Exception e) {
			System.out.println(castableService.findGains());
		}
		return json;	
	}

	private List<ClientGain> SumUpGainsByDate(List<ClientGain> clientGainsRaw) {
		// TODO Auto-generated method stub
		List<ClientGain> res = new ArrayList<ClientGain>();
		Map<Pair<LocalDate,Integer>,Integer> dicc= new HashMap<Pair<LocalDate,Integer>, Integer>();
		for(ClientGain clientGain: clientGainsRaw) {
			Pair<LocalDate,Integer> key = Pair.of(clientGain.getDate(),clientGain.getTableId());
			if (dicc.containsKey(key)) {
				Integer value = clientGain.getAmount() + dicc.get(key);
				dicc.put(key, value);				
			}else {
				dicc.put(key, clientGain.getAmount());
			}
		}
		Integer i = 1;
		for(Pair<LocalDate,Integer> key: dicc.keySet()) {
			ClientGain cg = new ClientGain();
			cg.setId(i);
			cg.setDate(key.getFirst());
			cg.setTableId(key.getSecond());
			cg.setAmount(dicc.get(key));
			res.add(cg);
			i++;
		}
		return res;
	}
	
}
