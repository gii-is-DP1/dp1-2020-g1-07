/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;


import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepo.save(user);
	}
	
	@Transactional(readOnly=true)
	public Optional<User> findUser(String username) {
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
}
