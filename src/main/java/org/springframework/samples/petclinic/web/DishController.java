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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dishes")
public class DishController {

	@Autowired
	private DishService dishService;
	
	@GetMapping()
	public String dishesList(ModelMap modelMap) {
		String vista= "dishes/dishesList";
		Iterable<Dish> dishes=dishService.findAll();
		modelMap.addAttribute("dishes", dishes);
		return vista;
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
	public String crearDish(ModelMap modelMap) {
		String view="dishes/addDish";
		modelMap.addAttribute("dish", new Dish());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarPlato(@Valid Dish dish, BindingResult result, ModelMap modelMap) {
		String view="dishes/dishesList";
		if(result.hasErrors()) {
			modelMap.addAttribute("dish", dish);
			return "dishes/addDish";
			
		}else {
			dishService.save(dish);
			modelMap.addAttribute("message", "Dish successfully saved!");
			view=dishesList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{dishId}")
	public String borrarDish(@PathVariable("dishId") int dishId, ModelMap modelMap) {
		String view="dishes/dishesList";
		Optional<Dish> dish = dishService.findDishById(dishId);
		if(dish.isPresent()) {
			dishService.delete(dish.get());
			modelMap.addAttribute("message", "Dish successfully deleted!");
			view=dishesList(modelMap);
		}else {
			modelMap.addAttribute("message", "Dish not found!");
			view=dishesList(modelMap);
		}
		return view;
	}
}
