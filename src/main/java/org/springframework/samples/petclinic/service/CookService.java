package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.repository.CookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
	
	public List<Dish> findPreparedDishes(int cookId) {
		// TODO Auto-generated method stub
		log.info("Loading prepared dishes from DB with the cook id: " + cookId);
		return cookRep.findPreparedDishes(cookId);
	}
	
	public List<Dish> findDishes() {
		// TODO Auto-generated method stub
		log.info("Loading dishes from DB");
		return cookRep.findDishes();
	}
}
