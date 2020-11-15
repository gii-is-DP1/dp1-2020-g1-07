package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.samples.petclinic.repository.CroupierRepository;
import org.springframework.transaction.annotation.Transactional;

public class CroupierService {
	@Autowired
	private  CroupierRepository croupierRep; 
	
	@Transactional
	public int croupierCount() {
		return (int)croupierRep.count();
	}
	
	@Transactional
	public Iterable<Croupier> findAll() {
		return croupierRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Croupier> findCroupierById(String id){ 
		return croupierRep.findById(id);
	}

	@Transactional
	public  void save(Croupier croupier) {   
		croupierRep.save(croupier);
	}

	public  void delete(Croupier croupier) { 
		croupierRep.delete(croupier);
	}
}
