package org.springframework.samples.petclinic.service;


import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	private UserRepository userRepo;

	@Autowired
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional
	public int userCount() {
		return (int)userRepo.count();
	}

	@Transactional
	public Iterable<User> findAll() {
		return userRepo.findAll();
	}
	
	@Transactional
	public void save(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepo.save(user);
	}
	
	@Transactional(readOnly=true)
	public Optional<User> findUserById(String username) {
		return userRepo.findById(username);
	}
	
	public  void delete(User user) { 
		userRepo.delete(user);
	}
	
	public Collection<Client> findClients() throws DataAccessException{
		log.info("Loading clients from DB");
		return userRepo.findClients();
	}
	
	public Collection<Employee> findEmployees() throws DataAccessException{
		log.info("Loading employees from DB");
		return userRepo.findEmployees();
	}
	
	public Collection<Administrator> findAdmins() throws DataAccessException{
		log.info("Loading admins from DB");
		return userRepo.findAdmins();
	}
	
	public Collection<Client> findClientsWithAccount() throws DataAccessException{
		log.info("Loading clients with account from DB");
		return userRepo.findClientsWithAccount();
	}
	
	public Collection<Employee> findEmployeesWithAccount() throws DataAccessException{
		log.info("Loading employees with account from DB");
		return userRepo.findEmployeesWithAccount();
	}
	
	public Collection<Administrator> findAdminsWithAccount() throws DataAccessException{
		log.info("Loading admins with account from DB");
		return userRepo.findAdminsWithAccount();
	}
	
	public Optional<Collection<Authority>> findAuthoritiesForUser(String username) throws DataAccessException{
		log.info("Looking for authorities from user: " + username);
		return userRepo.findAuthoritiesForUser(username);
	}
	
	public Collection<Employee> findEmployeesWithoutAccount() throws DataAccessException{
		log.info("Loading employees without account from DB");
		Collection<Employee> todos = userRepo.findEmployees();
		Collection<Employee> conUser = userRepo.findEmployeesWithAccount();
		return todos.parallelStream().filter(x -> !conUser.contains(x)).collect(Collectors.toList());
	}
	
	public Client findClientForUsername(String username) {
		log.info("Loading client with username: " + username);
		return userRepo.findClientForUsername(username);
	}
	
	public Employee findEmployeeForUsername(String username) {
		log.info("Loading employee with username: " + username);
		return userRepo.findEmployeeForUsername(username);
	}
}
