package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.service.MenuService;
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


import com.google.gson.Gson;


@Controller
@RequestMapping("/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@InitBinder("menu")
	public void initMenuBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new MenuValidator());
	}
	
	@GetMapping()
	public String menusList(ModelMap modelMap) {
		String vista= "menus/menusList";
		Iterable<Menu> menus=menuService.findAll();
		modelMap.addAttribute("menus", menus);
		return vista;
	}
	
	@GetMapping(path="/byDay")
	public String menusByDay(ModelMap modelMap) {
		String vista= "menus/menusByDay";
		Collection<LocalDate> list=menuService.findAllDates();
		Iterable<LocalDate> dates = list;
		modelMap.addAttribute("dates", dates);
		return vista;
	}
	
	@ResponseBody
	@RequestMapping(value = "/byDay/{date}", method = RequestMethod.GET)
	public String loadMenusByDate(@PathVariable("date")String datestr) {
		String json = "[";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<Menu> menus = new ArrayList<Menu>(menuService.findMenusByDate(date));
			for(Menu menu:menus) {
				json = json + "{\"id\":" + menu.getId() +","
						+ "\"name\":\"" + menu.getName() +"\","
						+ "\"date\":\"" + menu.getDate() +"\","
						+ "\"first_dish\":{"
							+ "\"id\":" + menu.getFirst_dish().getId() +","
							+ "\"name\":\"" + menu.getFirst_dish().getName() +"\","
							+ "\"dish_course\":{"
								+ "\"id\":" + menu.getFirst_dish().getDish_course().getId() + ","
								+ "\"name\":\"" + menu.getFirst_dish().getDish_course().getName() + "\"},"
							+ "\"shift\":{"
								+ "\"id\":" + menu.getFirst_dish().getShift().getId() + ","
								+ "\"name\":\"" + menu.getFirst_dish().getShift().getName() + "\"}},"
						+ "\"second_dish\":{"
							+ "\"id\":" + menu.getSecond_dish().getId() +","
							+ "\"name\":\"" + menu.getSecond_dish().getName() +"\","
							+ "\"dish_course\":{"
								+ "\"id\":" + menu.getSecond_dish().getDish_course().getId() + ","
								+ "\"name\":\"" + menu.getSecond_dish().getDish_course().getName() + "\"},"
							+ "\"shift\":{"
								+ "\"id\":" + menu.getSecond_dish().getShift().getId() + ","
								+ "\"name\":\"" + menu.getSecond_dish().getShift().getName() + "\"}},"
						+ "\"dessert\":{"
							+ "\"id\":" + menu.getDessert().getId() +","
							+ "\"name\":\"" + menu.getDessert().getName() +"\","
							+ "\"dish_course\":{"
								+ "\"id\":" + menu.getDessert().getDish_course().getId() + ","
								+ "\"name\":\"" + menu.getDessert().getDish_course().getName() + "\"},"
							+ "\"shift\":{"
								+ "\"id\":" + menu.getDessert().getShift().getId() + ","
								+ "\"name\":\"" + menu.getDessert().getShift().getName() + "\"}}},";
				if(menus.indexOf(menu)==menus.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(menus.size()==0) {
				json = json.substring(0, json.length() - 1) + "]";
			}
		}catch(Exception e) {
			System.out.println(menuService.findMenusByDate(date));
		}
		return json;
	}
	
	@GetMapping(path="/new")
	public String crearMenu(ModelMap modelMap) {
		String view="menus/addMenu";
		modelMap.addAttribute("menu", new Menu());
		return view;
	}
	
	@PostMapping(path="/save")
	public String salvarPlato(@Valid Menu menu, BindingResult result, ModelMap modelMap) {
		String view="menus/menusList";
		if(result.hasErrors()) {
			modelMap.addAttribute("menu", menu);
			return "menus/addMenu";
			
		}else {
			
			menuService.save(menu);
			
			modelMap.addAttribute("message", "Menu successfully saved!");
			view=menusList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{menuId}")
	public String borrarMenu(@PathVariable("menuId") int menuId, ModelMap modelMap) {
		String view="menus/menusList";
		Optional<Menu> menu = menuService.findMenuById(menuId);
		if(menu.isPresent()) {
			menuService.delete(menu.get());
			modelMap.addAttribute("message", "Menu successfully deleted!");
			view=menusList(modelMap);
		}else {
			modelMap.addAttribute("message", "Menu not found!");
			view=menusList(modelMap);
		}
		return view;
	}

	@GetMapping(value = "/{menuId}/edit")
	public String initUpdateCasTbForm(@PathVariable("menuId") int menuId, ModelMap model) {
		Menu menu = menuService.findMenuById(menuId).get();
		
		model.put("menu", menu);
		return "menus/updateMenu";
	}

	@PostMapping(value = "/{menuId}/edit")
	public String processUpdateCasTbForm(@Valid Menu menu, BindingResult result,
			@PathVariable("menuId") int menuId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("menu", menu);
			return "menus/updateMenu";
		}
		else {
			menu.setId(menuId);
			this.menuService.save(menu);
			return "redirect:/menus";
		}

	}
	
	@ModelAttribute("first_dishes")
	public Collection<Dish> populateFirstDishes() {
		return this.menuService.findFirstDishes();
	}
	
	@ResponseBody
	@RequestMapping(value = "/new/loadDishesByShift/{id}", method = RequestMethod.GET)
	public String loadFirstDishesByShift(@PathVariable("id")int id) {
		Gson gson = new Gson();
		String json = "[{";
		try {
			List<Dish> first_dishes = new ArrayList<Dish>(menuService.findFirstDishesByShift(id));
			for(Dish dish:first_dishes) {
				json = json + "\"id\":" + dish.getId() +","
						+ "\"name\":\"" + dish.getName() +"\","
						+ "\"dish_course\":{"
							+ "\"id\":" + dish.getDish_course().getId() + ","
							+ "\"name\":\"" + dish.getDish_course().getName() + "\"},"
						+ "\"shift\":{"
							+ "\"id\":" + dish.getShift().getId() + ","
							+ "\"name\":\"" + dish.getShift().getName() + "\"}},";
				if(first_dishes.indexOf(dish)==first_dishes.size()-1) {
					json = json.substring(0, json.length() - 1) + "]#[{";
				}
			}
			
			if(first_dishes.size()==0) {
				json = json.substring(0, json.length() - 1) + "]#[{";
			}
			
			List<Dish> second_dishes = new ArrayList<Dish>(menuService.findSecondDishesByShift(id));
			for(Dish dish:second_dishes) {
				json = json + "\"id\":" + dish.getId() +","
						+ "\"name\":\"" + dish.getName() +"\","
						+ "\"dish_course\":{"
							+ "\"id\":" + dish.getDish_course().getId() + ","
							+ "\"name\":\"" + dish.getDish_course().getName() + "\"},"
						+ "\"shift\":{"
							+ "\"id\":" + dish.getShift().getId() + ","
							+ "\"name\":\"" + dish.getShift().getName() + "\"}},";
				if(second_dishes.indexOf(dish)==second_dishes.size()-1) {
					json = json.substring(0, json.length() - 1) + "]#[{";
				}
			}
			if(second_dishes.size()==0) {
				json = json.substring(0, json.length() - 1) + "]#[{";
			}
			
			List<Dish> desserts = new ArrayList<Dish>(menuService.findDessertsByShift(id));
			for(Dish dish:desserts) {
				json = json + "\"id\":" + dish.getId() +","
						+ "\"name\":\"" + dish.getName() +"\","
						+ "\"dish_course\":{"
							+ "\"id\":" + dish.getDish_course().getId() + ","
							+ "\"name\":\"" + dish.getDish_course().getName() + "\"},"
						+ "\"shift\":{"
							+ "\"id\":" + dish.getShift().getId() + ","
							+ "\"name\":\"" + dish.getShift().getName() + "\"}},";
				if(desserts.indexOf(dish)==desserts.size()-1) {
					json = json.substring(0, json.length() - 1) + "]";
				}
			}
			if(desserts.size()==0) {
				json = json.substring(0, json.length() - 1) + "]";
			}
		}catch(Exception e) {
			System.out.println(menuService.findFirstDishesByShift(id));
		}
		return json;
	}
	
	
	@ModelAttribute("second_dishes")
	public Collection<Dish> populateSecondDishes() {
		return this.menuService.findSecondDishes();
	}
	
	@ModelAttribute("desserts")																								
	public Collection<Dish> populateDesserts() {
		return this.menuService.findDesserts();
	}
	
	@ModelAttribute("shifts")
	public Collection<Shift> populateShifts() {
		return this.menuService.findShifts();
	}
}
