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

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
		log.info("Loading list of clients");
		String vista= "clients/clientsList";
		log.info("Loading list of casinotables");
		Iterable<Client> clients=clientService.findAll();
		modelMap.addAttribute("clients", clients);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String createClient(ModelMap modelMap) {
		log.info("Loading new client form");
		String view="clients/addClient";
		modelMap.addAttribute("client", new Client());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveClient(@Valid Client client, BindingResult result, ModelMap modelMap) {
		log.info("Saving client:" + client.getId());
		String view="clients/clientsList";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("client", client);
			return "clients/addClient";
			
		}else {
			if(clientValidator.getClientwithIdDifferent(client.getDni())) {
				log.warn("Client duplicated");
				result.rejectValue("dni", "dni.duplicate", "El dni esta repetido");
				modelMap.addAttribute("client", client);
				return "clients/addClient";
			}
			log.info("Client validated: saving into DB");
			clientService.save(client);
			modelMap.addAttribute("message", "Clients successfully saved!");
			view=clientsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{clientId}")
	public String deleteClient(@PathVariable("clientId") int clientId, ModelMap modelMap) {
		log.info("Deleting client:" + clientId);
		String view="clients/clientsList";
		Optional<Client> client = clientService.findClientById(clientId);
		if(client.isPresent()) {
			log.info("Client found: deleting");
			Client cl = client.get();
			cgainService.findClientGainsForClient(cl.getDni()).forEach(x -> cgainService.delete(x));
			restReserService.findRestaurantReservationForClient(cl.getDni()).forEach(x -> restReserService.delete(x));
			showResService.findShowReservationForClient(cl.getDni()).forEach(x -> showResService.delete(x));
			uservice.delete(cl.getUser());
			clientService.delete(cl);
			modelMap.addAttribute("message", "Client successfully deleted!");
			view=clientsList(modelMap);
		}else {
			log.warn("Client not found in DB: "+ clientId);
			modelMap.addAttribute("message", "Client not found!");
			view=clientsList(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{clientId}/edit")
	public String initUpdateClientForm(@PathVariable("clientId") int clientId, ModelMap model) {
		log.info("Loading update client form: " + clientId);
		Client client = clientService.findClientById(clientId).get();
		
		model.put("client", client);
		return "clients/updateClient";
	}

	@PostMapping(value = "/{clientId}/edit")
	public String processUpdateClientForm(@Valid Client client, BindingResult result,
			@PathVariable("clientId") int clientId, ModelMap model) {
		log.info("Updating clientId: " + clientId);
		client.setId(clientId);
		if (result.hasErrors()) {
			log.warn("Found errors on update: " + result.getAllErrors());
			model.put("client", client);
			return "clients/updateClient";
		}
		else {
			if(clientValidator.getClientwithIdDifferent(client.getDni(), client.getId())) {
				log.warn("Client duplicated");
				result.rejectValue("dni", "dni.duplicate", "El dni esta repetido");
				model.put("client", client);
				return "clients/updateClient";
			}
			log.info("Client validated: updating into DB");
			this.clientService.save(client);
			return "redirect:/clients";
		}
	}
	

}
