package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.service.ClientGainService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.ShowReservationService;
import org.springframework.samples.petclinic.service.UserService;
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
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientGainService cgainService;
	
	@Autowired
	private RestaurantReservationService restReserService;
	
	@Autowired
	private ShowReservationService showResService;
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	private ClientValidator clientValidator;

	@InitBinder("client")
	public void initClientBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(clientValidator);
	}
	
	@GetMapping()
	public String clientsList(ModelMap modelMap) {
		String vista= "clients/clientsList";
		Iterable<Client> clients=clientService.findAll();
		modelMap.addAttribute("clients", clients);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String createClient(ModelMap modelMap) {
		String view="clients/addClient";
		modelMap.addAttribute("client", new Client());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveClient(@Valid Client client, BindingResult result, ModelMap modelMap) {
		String view="clients/clientsList";
		if(result.hasErrors()) {
			modelMap.addAttribute("client", client);
			return "clients/addClient";
			
		}else {
			if(clientValidator.getClientwithIdDifferent(client.getDni())) {
				result.rejectValue("dni", "dni.duplicate", "El dni esta repetido");
				modelMap.addAttribute("client", client);
				return "clients/addClient";
			}
			clientService.save(client);
			modelMap.addAttribute("message", "Clients successfully saved!");
			view=clientsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{clientId}")
	public String deleteClient(@PathVariable("clientId") int clientId, ModelMap modelMap) {
		String view="clients/clientsList";
		Optional<Client> client = clientService.findClientById(clientId);
		if(client.isPresent()) {
			Client cl = client.get();
			cgainService.findClientGainsForClient(cl.getDni()).forEach(x -> cgainService.delete(x));
			restReserService.findRestaurantReservationForClient(cl.getDni()).forEach(x -> restReserService.delete(x));
			showResService.findShowReservationForClient(cl.getDni()).forEach(x -> showResService.delete(x));
			uservice.delete(cl.getUser());
			clientService.delete(cl);
			modelMap.addAttribute("message", "Client successfully deleted!");
			view=clientsList(modelMap);
		}else {
			modelMap.addAttribute("message", "Client not found!");
			view=clientsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{clientId}/edit")
	public String initUpdateClientForm(@PathVariable("clientId") int clientId, ModelMap model) {
		Client client = clientService.findClientById(clientId).get();
		
		model.put("client", client);
		return "clients/updateClient";
	}

	@PostMapping(value = "/{clientId}/edit")
	public String processUpdateClientForm(@Valid Client client, BindingResult result,
			@PathVariable("clientId") int clientId, ModelMap model) {
		client.setId(clientId);
		if (result.hasErrors()) {
			model.put("client", client);
			return "clients/updateClient";
		}
		else {
			if(clientValidator.getClientwithIdDifferent(client.getDni(), client.getId())) {
				result.rejectValue("dni", "dni.duplicate", "El dni esta repetido");
				model.put("client", client);
				return "clients/updateClient";
			}
			this.clientService.save(client);
			return "redirect:/clients";
		}
	}
	

}
