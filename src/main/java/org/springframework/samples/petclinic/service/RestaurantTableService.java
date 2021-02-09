package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class RestaurantTableService {
private  RestaurantTableRepository restaurantTablerepo; 
	
	@Autowired
	public RestaurantTableService(RestaurantTableRepository restaurantTablerepo) {
		this.restaurantTablerepo = restaurantTablerepo;
	}
	
	@Transactional
	public int restaurantTableCount() {
		return (int)restaurantTablerepo.count();
	}
	
	@Transactional
	public Iterable<RestaurantTable> findAll() {
		return restaurantTablerepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<RestaurantTable> findRestaurantTableId(int id){ 
		return restaurantTablerepo.findById(id);
	}

	@Transactional
	public  void save(RestaurantTable restaurantTable) {   
		restaurantTablerepo.save(restaurantTable);
	}

	public  void delete(RestaurantTable restaurantTable) { 
		restaurantTablerepo.delete(restaurantTable);
	}
	
	@Transactional
	public Collection<RestaurantTable> findRestaurantTables() {
		log.info("Loading restaurante tables from DB");
		return restaurantTablerepo.findRestaurantTables();
	}
	
	@Transactional
	public Collection<Waiter> findWaiters() {
		log.info("Loading waiters from DB");
		return restaurantTablerepo.findWaiters();
	}
}
