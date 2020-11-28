package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.service.ClientService;
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
	
	@InitBinder("client")
	public void initClientBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ClientValidator());
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
			clientService.delete(client.get());
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
		if (result.hasErrors()) {
			model.put("client", client);
			return "clients/updateClient";
		}
		else {
			client.setId(clientId);
			this.clientService.save(client);
			return "redirect:/clients";
		}
	}
	

}
