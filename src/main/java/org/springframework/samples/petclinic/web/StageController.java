package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.StageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stages")
public class StageController {
	
	@Autowired
	private StageService stageService;
	
	@GetMapping()
	public String stagesListed(ModelMap modelMap) {
		String view = "stages/listStage";
		Iterable<Stage> stages=stageService.findAll();
		modelMap.addAttribute("stages", stages);
		return view;
	}
	

	@GetMapping(path="/new")
	public String createStage(ModelMap modelMap) {
		String view="stages/addStage";
		modelMap.addAttribute("stage", new Stage());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveStage(@Valid Stage stage, BindingResult result, ModelMap modelMap) {
		String view="stages/listStage";
		if(result.hasErrors()) {
			modelMap.addAttribute("stage", stage);
			return "stages/addStage";
			
		}else {
			
			stageService.save(stage);
			
			modelMap.addAttribute("message", "Stage successfully saved!");
			view=stagesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{stageId}")
	public String deleteStage(@PathVariable("stageId") int stageId, ModelMap modelMap) {
		String view="stages/listStage";
		Optional<Stage> stage = stageService.findStagebyId(stageId);
		if(stage.isPresent()) {
			stageService.delete(stage.get());
			modelMap.addAttribute("message", "Stage successfully deleted!");
			view=stagesListed(modelMap);
		}else {
			modelMap.addAttribute("message", "Stage not found!");
			view=stagesListed(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{stageId}/edit")
	public String initUpdateStageForm(@PathVariable("stageId") int stageId, ModelMap model) {
		Stage stage = stageService.findStagebyId (stageId).get();
		
		model.put("stage", stage);
		return "stages/updateStage";
	}

	@PostMapping(value = "/{stageId}/edit")
	public String processUpdateStageForm(@Valid Stage stage, BindingResult result,
			@PathVariable("stageId") int stageId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("stage", stage);
			return "stages/updateStage";
		}
		else {
			stage.setId(stageId);
			this.stageService.save(stage);
			return "redirect:/stages";
		}
	}
	@ModelAttribute("events")
    public Collection<Event> populateEvents() {
        return this.stageService.findEvents();
    }
	
	
	
}
