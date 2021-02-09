package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.model.Chef;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.MaintenanceWorker;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.service.AdministratorService;
import org.springframework.samples.petclinic.service.ArtistService;
import org.springframework.samples.petclinic.service.AuthorityService;
import org.springframework.samples.petclinic.service.ChefService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CookService;
import org.springframework.samples.petclinic.service.CroupierService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.MaintenanceWorkerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.WaiterService;
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
	private EmployeeService employeeService;
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private CroupierService croupierService;
	
	@Autowired
	private MaintenanceWorkerService mworkerService;
	
	@Autowired
	private WaiterService waiterService;
	
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
	
	@ModelAttribute("emps")
	public Collection<Employee> employeesNoAccount() {
		return this.userService.findEmployeesWithoutAccount();
	}
	
	@GetMapping(path="/new")
	public String createUser(ModelMap modelMap) {
		log.info("Loading new user form");
		String view="users/addUser";
		modelMap.addAttribute("employee", new Employee());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveUser(@Valid Employee employee, BindingResult result, ModelMap modelMap) {
		log.info("Saving user for employee: " + employee.getDni());	
		String view="users/addUser";
		if(result.hasErrors()) {
			log.warn("Found errors on insertion: " + result.getAllErrors());
			modelMap.addAttribute("error", result.getFieldError().getDefaultMessage());
			modelMap.addAttribute("employee", employee);
			return view;
			
		}else {
			User user = employee.getUser();
			if (validator.getUserwithIdDifferent(user.getUsername())) {
				log.warn("Couldn't create user, username " + user.getUsername() + " is taken");
				result.rejectValue("username", "username.duplicate", "Username " + user.getUsername() + " is taken");
				modelMap.addAttribute("employee", employee);
				return view;
			}
			log.info("User validated: saving into DB");
			Employee emp = employeeService.findEmployeeForDni(employee.getDni());
			String role = "employee";
			
			if (chefService.findChefById(emp.getId()).isPresent()) {
				role = "admin";
				Chef chef = chefService.findChefById(emp.getId()).get();
				chef.setUser(user);
				chefService.save(chef);
			}
			
			else if (adminService.findAdministratorById(emp.getId()).isPresent()) {
				role = "admin";
				Administrator admin = adminService.findAdministratorById(emp.getId()).get();
				admin.setUser(user);
				adminService.save(admin);
			}
			
			else if (artistService.findArtistById(emp.getId()).isPresent()) {
				Artist artist = artistService.findArtistById(emp.getId()).get();
				artist.setUser(user);
				artistService.save(artist);
			}
			
			else if (cookService.findCookById(emp.getId()).isPresent()) {
				Cook cook = cookService.findCookById(emp.getId()).get();
				cook.setUser(user);
				cookService.save(cook);
			}
			
			else if (croupierService.findCroupierById(emp.getId()).isPresent()) {
				Croupier croupier = croupierService.findCroupierById(emp.getId()).get();
				croupier.setUser(user);
				croupierService.save(croupier);
			}
			
			else if (mworkerService.findMaintenanceWorkerById(emp.getId()).isPresent()) {
				MaintenanceWorker mworker = mworkerService.findMaintenanceWorkerById(emp.getId()).get();
				mworker.setUser(user);
				mworkerService.save(mworker);
			}
			
			else if (waiterService.findWaiterById(emp.getId()).isPresent()) {
				Waiter waiter = waiterService.findWaiterById(emp.getId()).get();
				waiter.setUser(user);
				waiterService.save(waiter);
			}
			
			Authority auth = new Authority();
			auth.setAuthority(role);
			authService.save(auth);
			modelMap.addAttribute("message", "User successfully saved!");
			view=listUsers(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/register")
    public String createClientUser(ModelMap modelMap) {
        log.info("Loading new user form for clients");
        String view="users/addClientUser";
        User user = new User();
        Client cl = new Client();
        cl.setUser(user);
        modelMap.addAttribute("client", cl);
        return view;
    }
	
	@PostMapping(path="/saveclient")
    public String saveClientAndUser(@Valid Client client, BindingResult result, ModelMap modelMap) {
        log.info("Saving client and user: " + client.getDni());
        String view="users/addClientUser";
        if(result.hasErrors()) {
            log.warn("Found errors on insertion: " + result.getAllErrors());
            modelMap.addAttribute("error", result.getFieldError().getDefaultMessage());
            modelMap.addAttribute("client", client);
            return view;

        }else {
            User user = client.getUser();
            if (validator.getUserwithIdDifferent(user.getUsername())) {
                log.warn("Couldn't create user, username " + user.getUsername() + " is taken");
                result.rejectValue("username", "username.duplicate", "Username " + user.getUsername() + " is taken");
                modelMap.addAttribute("user", user);
                return view;
            }
            log.info("User validated: saving into DB");
            clientService.save(client);
            Authority auth = new Authority();
            auth.setAuthority("client");
            authService.save(auth);
            modelMap.addAttribute("message", "User successfully saved!");
            view="redirect:/";
        }
        return view;
    }
	
	@GetMapping(path="/delete/{userId}")
	public String deleteUser(@PathVariable("userId") String userId, ModelMap modelMap) {
		log.info("Deleting user: " + userId);
		String view="users/listUser";
		Optional<User> user = userService.findUserById(userId);
		if(user.isPresent()) {
			User us = user.get();
			log.info("User found: deleting");
			Collection<Authority> auths = userService.findAuthoritiesForUser(userId).get();
			auths.forEach(x -> authService.delete(x));
			if(auths.parallelStream().filter(x -> x.getAuthority().equals("client")).findAny().isPresent()) {
				Client cl = userService.findClientForUsername(us.getUsername());
				cl.setUser(null);
				clientService.save(cl);
			}
			else {
				Employee emp = userService.findEmployeeForUsername(us.getUsername());
				emp.setUser(null);
				employeeService.save(emp);
			}
			userService.delete(us);
			modelMap.addAttribute("message", "User successfully deleted!");
			view=listUsers(modelMap);
		}else {
			log.warn("User not found in DB: " + userId);
			modelMap.addAttribute("message", "User not found!");
			view=listUsers(modelMap);
		}
		return view;
	}

}
