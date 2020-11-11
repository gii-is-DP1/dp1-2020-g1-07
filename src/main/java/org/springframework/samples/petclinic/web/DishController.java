package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dishes")
public class DishController {

	@Autowired
	private DishService dishService;
	
	@GetMapping()
	public String listadoPlatos(ModelMap modelMap) {
		String vista= "dishes/listadoPlatos";
		Iterable<Dish> dishes=dishService.findAll();
		modelMap.addAttribute("dishes", dishes);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearDish(ModelMap modelMap) {
		String view="dishes/addDish";
		modelMap.addAttribute("Dish", new Dish());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarPlato(@Valid Dish dish, BindingResult result, ModelMap modelMap) {
		String view="dishes/listadoPlatos";
		if(result.hasErrors()) {
			modelMap.addAttribute("dish", dish);
			return "dishes/editDish";
			
		}else {
			
			dishService.save(dish);
			
			modelMap.addAttribute("message", "Dish successfully saved!");
			view=listadPlatos(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{dishId}")
	public String borrarDish(@PathVariable("dishId") int dishId, ModelMap modelMap) {
		String view="dishes/listadoPlatos";
		Optional<Dish> dish = dishService.findDishById(dishId);
		if(dish.isPresent()) {
			dishService.delete(dish.get());
			modelMap.addAttribute("message", "Dish successfully deleted!");
			view=listadoPlatos(modelMap);
		}else {
			modelMap.addAttribute("message", "Dish not found!");
			view=listadoPlatos(modelMap);
		}
		return view;
	}
}
