package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.repository.CookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CookService {
	
	private  CookRepository cookRep;
	
	@Autowired
	public CookService(CookRepository cookRep) {
		this.cookRep = cookRep;
	}
	
	@Transactional
	public int cookCount() {
		return (int)cookRep.count();
	}
	
	@Transactional
	public Iterable<Cook> findAll() {
		return cookRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Cook> findCookById(int id){ 
		return cookRep.findById(id);
	}

	@Transactional
	public  void save(Cook cook) {   
		cookRep.save(cook);
	}

	public  void delete(Cook cook) { 
		cookRep.delete(cook);
	}
}
