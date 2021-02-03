package org.springframework.samples.petclinic.service;


import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
		userRepo.save(user);
	}
	
	@Transactional(readOnly=true)
	public Optional<User> findUserById(String username) {
		return userRepo.findById(username);
	}
	
	public  void delete(User user) { 
		userRepo.delete(user);
	}
	
	public Collection<Employee> findEmployees() throws DataAccessException{
		log.info("Loading employees from DB");
		return userRepo.findEmployees();
	}
	
	public Collection<Client> findClients() throws DataAccessException{
		log.info("Loading clients from DB");
		return userRepo.findClients();
	}
	
	public Collection<Authority> findAuthorities() throws DataAccessException{
		log.info("Loading authorities from DB");
		return userRepo.findAuthorities();
	}
}
