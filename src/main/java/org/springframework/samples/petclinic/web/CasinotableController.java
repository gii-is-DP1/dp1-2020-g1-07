package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.service.CasinotableService;
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

@Controller
@RequestMapping("/casinotables")
public class CasinotableController {

	 
	@Autowired
	private CasinotableService castableService;
	
	@GetMapping()
	public String casinotablesListed(ModelMap modelMap) {
		String view = "casinotables/listCasinotable";
		Iterable<Casinotable> casinotables=castableService.findAll();
		modelMap.addAttribute("casinotables", casinotables);
		return view;
	}
	
	
	@GetMapping(path="/new")
	public String createCasinotable(ModelMap modelMap) {
		String view="casinotables/addCasinotable";
		modelMap.addAttribute("casinotable", new Casinotable());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveCasinotable(@Valid Casinotable casinotable, BindingResult result, ModelMap modelMap) {
		String view="casinotables/listCasinotable";
		if(result.hasErrors()) {
			modelMap.addAttribute("casinotable", casinotable);
			return "casinotables/addCasinotable";
			
		}else {
			
			castableService.save(casinotable);
			
			modelMap.addAttribute("message", "Casinotable successfully saved!");
			view=casinotablesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{casinotableId}")
	public String deleteCasinoTable(@PathVariable("casinotableId") int casinotableId, ModelMap modelMap) {
		String view="casinotables/listCasinotable";
		Optional<Casinotable> casinotable = castableService.findCasinotableById(casinotableId);
		if(casinotable.isPresent()) {
			castableService.delete(casinotable.get());
			modelMap.addAttribute("message", "Casinotable successfully deleted!");
			view=casinotablesListed(modelMap);
		}else {
			modelMap.addAttribute("message", "Casinotable not found!");
			view=casinotablesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{casinotableId}/edit")
	public String initUpdateCasTbForm(@PathVariable("casinotableId") int casinotableId, ModelMap model) {
		Casinotable casinotable = castableService.findCasinotableById(casinotableId).get();
		
		model.put("casinotable", casinotable);
		return "casinotables/updateCasinotable";
	}

	@PostMapping(value = "/{casinotableId}/edit")
	public String processUpdateCasTbForm(@Valid Casinotable casinotable, BindingResult result,
			@PathVariable("casinotableId") int casinotableId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("casinotable", casinotable);
			return "casinotables/updateCasinotable";
		}
		else {
			casinotable.setId(casinotableId);
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
        String json = "[{";
        try {
            List<Game> games = new ArrayList<Game>(castableService.findGamesByGameType(id));
            for(Game game:games) {
                json = json + "\"id\":" + game.getId() +","
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
            

        }catch(Exception e) {
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
}
