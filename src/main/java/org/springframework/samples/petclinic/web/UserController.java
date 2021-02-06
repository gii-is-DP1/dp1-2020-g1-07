package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthorityService;
import org.springframework.samples.petclinic.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserValidator validator;
	
	@InitBinder("user")
	public void initUserBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listUsers(ModelMap modelMap) {
		log.info("Loading list of users view");
		String view= "users/listUser";
		Iterable<User> users=userService.findAll();
		modelMap.addAttribute("users", users);
		Collection<Client> clients = userService.findClientsWithAccount();
		modelMap.addAttribute("logclients", clients);
		Collection<Administrator> admins = userService.findAdminsWithAccount();
		modelMap.addAttribute("logadmins", admins);
		Collection<Employee> employees = userService.findEmployeesWithAccount();
		modelMap.addAttribute("logemployees", employees);
		return view;
	}
	
	@ModelAttribute("clients")
	public Collection<Client> clients() {
		return this.userService.findClients();
	}
	
	@ModelAttribute("admins")
	public Collection<Administrator> admins() {
		return this.userService.findAdmins();
	}
	
	@ModelAttribute("employees")
	public Collection<Employee> employees() {
		return this.userService.findEmployees();
	}
	
	@GetMapping(path="/newlog")
	public String createUser(ModelMap modelMap) {
		log.info("Loading new user form");
		String view="users/addUser";
		modelMap.addAttribute("user", new User());
		return view;
	}
	
	@GetMapping(path="/new")
	public String createClientUser(ModelMap modelMap) {
		log.info("Loading new user form for clients");
		String view="users/addClient";
		modelMap.addAttribute("user", new User());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveUser(@Valid User user, @RequestParam("role") String role, 
			@RequestParam("origin") String origin, BindingResult result, ModelMap modelMap) {
		//TODO: Conectar con la vista de crear cliente (guardar el usuario dentro del cliente)
		//Y con la de crear empleado/admin
		log.info("Saving user: " + user.getUsername());	
		String view="users/addClient";
			if (origin.equals("admin"))
				view="users/addUser";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("user", user);
			modelMap.addAttribute("role", role);
			return view;
			
		}else {
			if (validator.getUserwithIdDifferent(user.getUsername())) {
				log.warn("Couldn't create user, username " + user.getUsername() + " is taken");
				result.rejectValue("username", "username.duplicate", "Username " + user.getUsername() + " is taken");
				modelMap.addAttribute("user", user);
				modelMap.addAttribute("role", role);
				return view;
			}
			log.info("User validated: saving into DB");
			Authority auth = new Authority();
			auth.setAuthority(role);
			userService.save(user);
			authService.save(auth);
			modelMap.addAttribute("message", "User successfully saved!");
			//view="home"; TODO: Redirección a la vista de bienvenida
			//if (origin.equals("admin"))	
				view=listUsers(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{userId}")
	public String deleteUser(@PathVariable("userId") String userId, ModelMap modelMap) {
		log.info("Deleting user: " + userId);
		String view="users/listUser";
		Optional<User> user = userService.findUserById(userId);
		if(user.isPresent()) {
			log.info("User found: deleting");
			Collection<Integer> authids = userService.findAuthorityId(userId);
			userService.delete(user.get());
			authids.forEach(id -> authService.delete(authService.findAuthorityById(id).get()));
			modelMap.addAttribute("message", "User successfully deleted!");
			view=listUsers(modelMap);
		}else {
			log.warn("User not found in DB: " + userId);
			modelMap.addAttribute("message", "User not found!");
			view=listUsers(modelMap);
		}
		return view;
	}
	/*
	@GetMapping(value = "/{userId}/edit")
    public String initUpdateUserForm(@PathVariable("userId") int userId, ModelMap model) {
		log.info("Loading update user form");
		User user = userService.findUserById(userId).get();
        model.put("user", user);
        return "users/updateUser";
    }

    @PostMapping(value = "/{userId}/edit")
    public String processUpdateUserForm(@Valid User user, BindingResult result,
            @PathVariable("userId") int userId, ModelMap model) {
    	log.info("Updating user: " + userId);
    	user.setId(userId);
        if (result.hasErrors()) {
        	log.warn("Found errors on update: " + result.getAllErrors());
            model.put("user", user);
            return "users/updateUser";
        }
        else {
        	if (validator.getUserwithIdDifferent(user.getUsername(), user.getId())) {
        		log.warn("Couldn't update user, username " + user.getUsername() + " is taken");
				result.rejectValue("username", "username.duplicate", "Username " + user.getUsername() + " is taken");
				model.addAttribute("user", user);
				return "users/updateUser";
			}
        	log.info("User validated: updating into DB");
            this.userService.save(user);
            return "redirect:/users";
        }
    }*/

}
