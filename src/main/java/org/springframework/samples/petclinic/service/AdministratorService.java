package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.repository.AdministratorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministratorService {
	@Autowired
	private  AdministratorRepository administratorRep; 
	
	@Transactional
	public int AdministratorCount() {
		return (int)administratorRep.count();
	}
	
	@Transactional
	public Iterable<Administrator> findAll() {
		return administratorRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Administrator> findAdministratorById(int id){ 
		return administratorRep.findById(id);
	}

	@Transactional
	public  void save(Administrator administrator) {   
		administratorRep.save(administrator);
	}

	public  void delete(Administrator administrator) { 
		administratorRep.delete(administrator);
	}
}
