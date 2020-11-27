package org.springframework.samples.petclinic.web;

import java.util.Collection;
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
	
	@ModelAttribute("first_dishes")
	public Collection<Dish> populateFirstDishes() {
		return this.menuService.findFirstDishes();
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
