package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Chef;
import org.springframework.samples.petclinic.repository.ChefRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChefService {
	@Autowired
	private  ChefRepository chefRep; 
	
	@Transactional
	public int chefCount() {
		return (int)chefRep.count();
	}
	
	@Transactional
	public Iterable<Chef> findAll() {
		return chefRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Chef> findChefById(int id){ 
		return chefRep.findById(id);
	}

	@Transactional
	public  void save(Chef chef) {   
		chefRep.save(chef);
	}

	public  void delete(Chef chef) { 
		chefRep.delete(chef);
	}
}
