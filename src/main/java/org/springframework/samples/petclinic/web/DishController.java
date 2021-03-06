package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.DishService;
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
@RequestMapping("/dishes")
public class DishController {

	@Autowired
	private DishService dishService;
	
	@Autowired
	private DishValidator dishValidator;
	
	@InitBinder("dish")
	public void initDishBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(dishValidator);
	}
	
	@GetMapping()
	public String dishesList(ModelMap modelMap) {
		log.info("Loading list of dishes view");
		String view= "dishes/dishesList";
		Iterable<Dish> dishes=dishService.findAll();
		modelMap.addAttribute("dishes", dishes);
		return view;
	}
	
	@ModelAttribute("dish_courses")
	public Collection<DishCourse> populateDishCourses() {
		return this.dishService.findDishCourses();
	}
	
	@ModelAttribute("shifts")
	public Collection<Shift> populateShifts() {
		return this.dishService.findShifts();
	}
	
	@GetMapping(path="/new")
	public String createDish(ModelMap modelMap) {
		log.info("Loading new dish form");
		String view="dishes/addDish";
		modelMap.addAttribute("dish", new Dish());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveDish(@Valid Dish dish, BindingResult result, ModelMap modelMap) {
		log.info("Saving dish: " + dish.getId());
		String view="dishes/dishesList";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("dish", dish);
			return "dishes/addDish";
		}else {
			if(dishValidator.getDishwithIdDifferent(dish.getName())) {
				log.warn("Name duplicated");
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				modelMap.addAttribute("dish", dish);
				return "dishes/addDish";
			}
			log.info("Dish validated: saving into DB");
			dishService.save(dish);
			modelMap.addAttribute("message", "Dish successfully saved!");
			view=dishesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{dishId}")
	public String deleteDish(@PathVariable("dishId") int dishId, ModelMap modelMap) {
		log.info("Deleting dish: " + dishId);
		String view="dishes/dishesList";
		Optional<Dish> dish = dishService.findDishById(dishId);
		if(dish.isPresent()) {
			if(dishValidator.isUsedInMenu(dish.get())) {
				log.warn("This dish can't be deleted, is in one of the menus");
				modelMap.addAttribute("message", "This dish can't be deleted, is in one of the menus!");
				view=dishesList(modelMap);
			}else {
				log.info("Dish found: deleting");
				dishService.delete(dish.get());
				modelMap.addAttribute("message", "Dish successfully deleted!");
				view=dishesList(modelMap);
			}
			
		}else {
			log.warn("Dish not found in DB: " + dishId);
			modelMap.addAttribute("message", "Dish not found!");
			view=dishesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{dishId}/edit")
	public String initUpdateCasTbForm(@PathVariable("dishId") int dishId, ModelMap model) {
		log.info("Loading update dish form");
		Dish dish = dishService.findDishById(dishId).get();	
		model.put("dish", dish);
		return "dishes/updateDish";
	}

	@PostMapping(value = "/{dishId}/edit")
	public String processUpdateCasTbForm(@Valid Dish dish, BindingResult result,
			@PathVariable("dishId") int dishId, ModelMap model) {
		dish.setId(dishId);
		log.info("Updating dish: " + dishId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("dish", dish);
			return "dishes/updateDish";
		}
		else {
			if(dishValidator.getDishwithIdDifferent(dish.getName(), dish.getId())) {
				log.warn("Name duplicated");
				result.rejectValue("name", "name.duplicate", "El nombre esta repetido");
				model.put("dish", dish);
				return "dishes/updateDish";
			}
			log.info("Cgain validated: updating into DB");
			this.dishService.save(dish);
			return "redirect:/dishes";
		}
	}
}
