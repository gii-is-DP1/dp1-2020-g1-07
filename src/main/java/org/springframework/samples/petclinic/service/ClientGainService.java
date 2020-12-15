package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClientGainRepository;
import org.springframework.transaction.annotation.Transactional;

public class ClientGainService {
	@Autowired
	private  ClientGainRepository cgainRepo;
	
	@Transactional
	public int cgainCount() {
		return (int)cgainRepo.count();
	}
	
	@Transactional
	public Iterable<ClientGain> findAll() {
		return cgainRepo.findAll();
	}
	
	@Transactional
	public Collection<LocalDate> findAllDates() {
		return cgainRepo.findAllDates();
	}
	
	@Transactional(readOnly=true)
	public  Optional<ClientGain> findClientGainById(int id){ 
		return cgainRepo.findById(id);
	}

	@Transactional
	public  void save(ClientGain cgain) {
		cgainRepo.save(cgain);
	}

	public  void delete(ClientGain cgain) { 
		cgainRepo.delete(cgain);
	}
	
	public Collection<User> findUsers() throws DataAccessException{
		// TODO Auto-generated method stub
		return cgainRepo.findUsers();
	}
}
