package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.repository.RestaurantTableRepository;
import org.springframework.transaction.annotation.Transactional;

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
		return restaurantTablerepo.findRestaurantTables();
	}
}
