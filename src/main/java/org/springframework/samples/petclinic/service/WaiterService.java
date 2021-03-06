package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.repository.WaiterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class WaiterService {
	
	private  WaiterRepository waiterRep;
	
	@Autowired
	public WaiterService(WaiterRepository waiterRep) {
		this.waiterRep = waiterRep;
	}
	
	@Transactional
	public int waiterCount() {
		return (int)waiterRep.count();
	}
	
	@Transactional
	public Iterable<Waiter> findAll() {
		return waiterRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Waiter> findWaiterById(int id){ 
		return waiterRep.findById(id);
	}

	@Transactional
	public  void save(Waiter waiter) {   
		waiterRep.save(waiter);
	}

	public  void delete(Waiter waiter) { 
		waiterRep.delete(waiter);
	}

	public List<RestaurantTable> findTablesServed(int waiterId) {
		// TODO Auto-generated method stub
		log.info("Loading served tables from DB with the waiter id: " + waiterId);
		return waiterRep.findTablesServed(waiterId);
	}
	
	public List<RestaurantTable> findRestaurantTables() {
		// TODO Auto-generated method stub
		log.info("Loading restaurant tables from DB");
		return waiterRep.findRestaurantTables();
	}
}
