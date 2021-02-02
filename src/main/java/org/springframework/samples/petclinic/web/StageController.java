package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.StageService;
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
@RequestMapping("/stages")
public class StageController {
	
	@Autowired
	private StageService stageService;
	@Autowired
	private StageValidator stageValidator;
	@InitBinder("stage")
	public void initMenuBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(stageValidator);
	}
	@GetMapping()
	public String stagesListed(ModelMap modelMap) {
		log.info("Loading list of stages view");
		String view = "stages/listStages";
		Iterable<Stage> stages=stageService.findAll();
		modelMap.addAttribute("stages", stages);
		return view;
	}
	

	@GetMapping(path="/new")
	public String createStage(ModelMap modelMap) {
		log.info("Loading new stage form");
		String view="stages/addStage";
		modelMap.addAttribute("stage", new Stage());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveStage(@Valid Stage stage, 
			BindingResult result, ModelMap modelMap) {
		log.info("Saving stage: " + stage.getId());
		String view="stages/listStages";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("stage", stage);
			return "stages/addStage";
			
		}else {
			log.info("Stage validated: saving into DB");
			stageService.save(stage);
			modelMap.addAttribute("message", "Stage successfully saved!");
			view=stagesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{stageId}")
	public String deleteStage(@PathVariable("stageId") int stageId, ModelMap modelMap) {
		log.info("Deleting stage: " + stageId);
		String view="stages/listStage";
		Optional<Stage> stage = stageService.findStagebyId(stageId);
		if(stage.isPresent()) {
			if(stageValidator.isUsedInEvent(stage.get())) {
				log.warn("The stage can't be deleted, is in one of the events");
				modelMap.addAttribute("message", "This stage can't be deleted, is in one of the events!");
				view=stagesListed(modelMap);
			}else {
			log.info("Stage found: deleting");				
			stageService.delete(stage.get());
			modelMap.addAttribute("message", "Stage successfully deleted!");
			view=stagesListed(modelMap);
			}
		}else {
			log.warn("Stage not found in DB: " + stageId);
			modelMap.addAttribute("message", "Stage not found!");
			view=stagesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{stageId}/edit")
	public String initUpdateStageForm(@PathVariable("stageId") int stageId, ModelMap model) {
		log.info("Loading update stage form");
		Stage stage = stageService.findStagebyId (stageId).get();
		model.put("stage", stage);
		return "stages/updateStage";
	}

	@PostMapping(value = "/{stageId}/edit")
	public String processUpdateStageForm(@Valid Stage stage, BindingResult result,
			@PathVariable("stageId") int stageId, ModelMap model) {
		log.info("Updating stage: " + stageId);
		stage.setId(stageId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("stage", stage);
			return "stages/updateStage";
		}
		else {
			log.info("Stage validated: updating into DB");
			this.stageService.save(stage);
			return "redirect:/stages";
		}
	}
	@ModelAttribute("events")
    public Collection<Event> populateEvents() {
        return this.stageService.findEvents();
    }
	
	
	
}
