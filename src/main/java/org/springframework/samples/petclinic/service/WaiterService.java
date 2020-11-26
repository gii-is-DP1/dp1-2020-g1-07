package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.repository.WaiterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WaiterService {
	@Autowired
	private  WaiterRepository waiterRep; 
	
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
}