package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.RestaurantTableService;
import org.springframework.samples.petclinic.service.WaiterService;
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
@RequestMapping("/waiters")
public class WaiterController {
	
	private List<RestaurantTable> servedTables;
	private List<Integer> servedTablesId;

	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private RestaurantTableService restaurantTableService;
	
	@Autowired
	private WaiterValidator validator;
	
	@InitBinder("waiter")
	public void initWaiterBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listWaiters(ModelMap modelMap) {
		String view= "waiters/listWaiter";
		Iterable<Waiter> waiters=waiterService.findAll();
		modelMap.addAttribute("waiters", waiters);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createWaiter(ModelMap modelMap) {
		String view="waiters/addWaiter";
		modelMap.addAttribute("waiter", new Waiter());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveWaiter(@Valid Waiter waiter, BindingResult result, ModelMap modelMap) {
		String view="waiters/listWaiter";
		if(result.hasErrors()) {
			modelMap.addAttribute("waiter", waiter);
			return "waiters/addWaiter";
			
		}else {
			if (validator.getWaiterwithIdDifferent(waiter.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Waiter with dni" + waiter.getDni() + "already in database");
				modelMap.addAttribute("waiter", waiter);
				return "waiters/addWaiter";
			}
			waiterService.save(waiter);
			
			modelMap.addAttribute("message", "Waiter successfully saved!");
			view=listWaiters(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{waiterId}")
	public String deleteWaiter(@PathVariable("waiterId") int waiterId, ModelMap modelMap) {
		String view="waiters/listWaiter";
		Optional<Waiter> waiter = waiterService.findWaiterById(waiterId);
		if(waiter.isPresent()) {
			waiterService.delete(waiter.get());
			modelMap.addAttribute("message", "Waiter successfully deleted!");
			view=listWaiters(modelMap);
		}else {
			modelMap.addAttribute("message", "Waiter not found!");
			view=listWaiters(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{waiterId}/edit")
    public String initUpdateWaiterForm(@PathVariable("waiterId") int waiterId, ModelMap model) {
		Waiter waiter = waiterService.findWaiterById(waiterId).get();
        model.put("waiter", waiter);
        return "waiters/updateWaiter";
    }

    @PostMapping(value = "/{waiterId}/edit")
    public String processUpdateWaiterForm(@Valid Waiter waiter, BindingResult result,
            @PathVariable("waiterId") int waiterId, ModelMap model) {
    	waiter.setId(waiterId);
        if (result.hasErrors()) {
            model.put("waiter", waiter);
            return "waiters/updateWaiter";
        }
        else {
        	if (validator.getWaiterwithIdDifferent(waiter.getDni(), waiter.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Waiter with dni" + waiter.getDni() + "already in database");
				model.addAttribute("waiter", waiter);
				return "waiters/updateWaiter";
			}
        	
            this.waiterService.save(waiter);
            return "redirect:/waiters";
        }
    }
    
    //Parte de controlador para las mesas
    
    
    @GetMapping(path="/serves/{waiterId}")
	public String waiterServes(@PathVariable("waiterId") int waiterId, ModelMap modelMap) {
		String view="waiters/assignedTables";
		Waiter waiter = waiterService.findWaiterById(waiterId).get();
		modelMap.put("waiter",waiter);
		servedTables = waiterService.findTablesServed(waiterId);
		servedTablesId = ObtainTablesIds(servedTables);
		modelMap.put("restaurantTables", servedTables);
		return view;
	}
    
    @GetMapping(path="/serves/{waiterId}/new")
	public String createServe(@PathVariable("waiterId") int waiterId, ModelMap modelMap) {
		String view="waiters/addServe";
		modelMap.put("restaurantTable", new RestaurantTable());
		//HAY QUE HACER UN BUSCAR TODAS LAS MESAS Y ELIMINAR LAS MESAS QUE ESTAN ALMACENADAS EN SERVEDTABLES, A ESA LISTA SE LE HACE EL OBTAINIDS
		List<RestaurantTable> restaurantTables = waiterService.findRestaurantTables();
		List<Integer> notServed = new ArrayList<Integer>();
		for(RestaurantTable restaurantTable:restaurantTables) {
			if(!servedTablesId.contains(restaurantTable.getId())) {
				notServed.add(restaurantTable.getId());
			}
		}
		modelMap.put("restaurantTablesIds", notServed);
		return view;
	}
	
    public List<Integer> ObtainTablesIds(List<RestaurantTable> restaurantTables){
    	List<Integer> res = new ArrayList<Integer>();
    	for(RestaurantTable restaurantTable:restaurantTables) {
    		res.add(restaurantTable.getId());
    	}
    	return res;
    }
    
	@PostMapping(path="/serves/{waiterId}/save")
	public String saveServe(@PathVariable("waiterId") int waiterId, RestaurantTable restaurantTable, BindingResult result, ModelMap modelMap) {
		String view="waiters/assignedTables";
		if(result.hasErrors() || restaurantTable.getId()==null || restaurantTable.getId()==0) {
			modelMap.addAttribute("restaurantTable", restaurantTable);
			modelMap.addAttribute("message", "There is no table to add!");
			return "waiters/addServe";
			
		}else {
			restaurantTable = restaurantTableService.findRestaurantTableId(restaurantTable.getId()).get();
			Waiter waiter = waiterService.findWaiterById(waiterId).get();
			Collection<RestaurantTable> served = waiter.getServes();
			served.add(restaurantTable);
			waiter.setServes(served);
			waiterService.save(waiter);
			modelMap.addAttribute("message", "The waiter will serve this table!");
			view=waiterServes(waiterId,modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/serves/{waiterId}/delete/{restaurantTableId}")
	public String deleteServe(@PathVariable("waiterId") int waiterId, @PathVariable("restaurantTableId") int restaurantTableId, ModelMap modelMap) {
		String view="waiters/assignedTables";
		Optional<Waiter> waiter = waiterService.findWaiterById(waiterId);
		Optional<RestaurantTable> restaurantTable = restaurantTableService.findRestaurantTableId(restaurantTableId);
		if(waiter.isPresent() && restaurantTable.isPresent()) {
			Collection<RestaurantTable> served = waiter.get().getServes();
			served.remove(restaurantTable.get());
			waiter.get().setServes(served);
			waiterService.save(waiter.get());
			modelMap.addAttribute("message", "Serve successfully deleted!");
			view=waiterServes(waiterId,modelMap);
		}else {
			modelMap.addAttribute("message", "Waiter or table not found!");
			view=waiterServes(waiterId,modelMap);
		}
		return view;
	}
    
    
    
}
