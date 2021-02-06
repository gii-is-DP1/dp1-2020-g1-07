package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.CookService;
import org.springframework.samples.petclinic.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cooks")
public class CookController {
	
	private List<Dish> preparedDishes;
	private List<Integer> preparedDishesId;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private CookValidator validator;
	
	@InitBinder("cook")
	public void initCookBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listCooks(ModelMap modelMap) {
		String view= "cooks/listCook";
		Iterable<Cook> cooks=cookService.findAll();
		modelMap.addAttribute("cooks", cooks);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createCook(ModelMap modelMap) {
		String view="cooks/addCook";
		modelMap.addAttribute("cook", new Cook());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveCook(@Valid Cook cook, BindingResult result, ModelMap modelMap) {
		String view="cooks/listCook";
		if(result.hasErrors()) {
			modelMap.addAttribute("cook", cook);
			return "cooks/addCook";
			
		}else {
			if (validator.getCookwithIdDifferent(cook.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Cook with dni" + cook.getDni() + "already in database");
				modelMap.addAttribute("cook", cook);
				return "cooks/addCook";
			}
			cookService.save(cook);
			
			modelMap.addAttribute("message", "Cook successfully saved!");
			view=listCooks(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{cookId}")
	public String deleteCook(@PathVariable("cookId") int cookId, ModelMap modelMap) {
		String view="cooks/listCook";
		Optional<Cook> cook = cookService.findCookById(cookId);
		if(cook.isPresent()) {
			cookService.delete(cook.get());
			modelMap.addAttribute("message", "Cook successfully deleted!");
			view=listCooks(modelMap);
		}else {
			modelMap.addAttribute("message", "Cook not found!");
			view=listCooks(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{cookId}/edit")
    public String initUpdateCookForm(@PathVariable("cookId") int cookId, ModelMap model) {
		Cook cook = cookService.findCookById(cookId).get();
        model.put("cook", cook);
        return "cooks/updateCook";
    }

    @PostMapping(value = "/{cookId}/edit")
    public String processUpdateCookForm(@Valid Cook cook, BindingResult result,
            @PathVariable("cookId") int cookId, ModelMap model) {
    	cook.setId(cookId);
        if (result.hasErrors()) {
            model.put("cook", cook);
            return "cooks/updateCook";
        }
        else {
        	if (validator.getCookwithIdDifferent(cook.getDni(), cook.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Cook with dni" + cook.getDni() + "already in database");
				model.addAttribute("cook", cook);
				return "cooks/updateCook";
			}
            this.cookService.save(cook);
            return "redirect:/cooks";
        }
    }
    
    //Parte de controlador para los platos
    
    
    @GetMapping(path="/prepares/{cookId}")
	public String cookPrepares(@PathVariable("cookId") int cookId, ModelMap modelMap) {
		String view="cooks/assignedDishes";
		Cook cook = cookService.findCookById(cookId).get();
		modelMap.put("cook",cook);
		preparedDishes = cookService.findPreparedDishes(cookId);
		preparedDishesId = ObtainDishesIds(preparedDishes);
		modelMap.put("preparedDishes", preparedDishes);
		return view;
	}
    
    @GetMapping(path="/prepares/{cookId}/new")
	public String createServe(@PathVariable("cookId") int cookId, ModelMap modelMap) {
		String view="cooks/addPrepare";
		modelMap.put("dish", new Dish());
		List<Dish> dishes = cookService.findDishes();
		List<String> notPrepared = new ArrayList<String>();
		for(Dish dish:dishes) {
			if(!preparedDishesId.contains(dish.getId())) {
				notPrepared.add(dish.getName());
			}
		}
		modelMap.put("dishesNames", notPrepared);
		return view;
	}
	
    public List<Integer> ObtainDishesIds(List<Dish> dishes){
    	List<Integer> res = new ArrayList<Integer>();
    	for(Dish dish:dishes) {
    		res.add(dish.getId());
    	}
    	return res;
    }
    
	@PostMapping(path="/prepares/{cookId}/save")
	public String saveServe(@PathVariable("cookId") int cookId, Dish dish, BindingResult result, ModelMap modelMap) {
		String view="cooks/assignedTables";
		if(result.hasErrors() || dish.getName()==null) {
			modelMap.addAttribute("dish", dish);
			modelMap.addAttribute("message", "There is no dish to add!");
			return "cooks/addPrepare";
			
		}else {
			dish = dishService.findDishByName(dish.getName()).get();
			Cook cook = cookService.findCookById(cookId).get();
			Collection<Dish> prepared = cook.getPrepares();
			prepared.add(dish);
			cook.setPrepares(prepared);
			cookService.save(cook);
			modelMap.addAttribute("message", "The cook will prepare this dish!");
			view=cookPrepares(cookId,modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/prepares/{cookId}/delete/{dishId}")
	public String deleteServe(@PathVariable("cookId") int cookId, @PathVariable("dishId") int dishId, ModelMap modelMap) {
		String view="cooks/assignedDishes";
		Optional<Cook> cook = cookService.findCookById(cookId);
		Optional<Dish> dish = dishService.findDishById(dishId);
		if(cook.isPresent() && dish.isPresent()) {
			Collection<Dish> prepared = cook.get().getPrepares();
			prepared.remove(dish.get());
			cook.get().setPrepares(prepared);
			cookService.save(cook.get());
			modelMap.addAttribute("message", "Prepare successfully deleted!");
			view=cookPrepares(cookId,modelMap);
		}else {
			modelMap.addAttribute("message", "Cook or dish not found!");
			view=cookPrepares(cookId,modelMap);
		}
		return view;
	}
}
