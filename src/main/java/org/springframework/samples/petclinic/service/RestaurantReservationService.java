package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.repository.RestaurantReservationRepository;
import org.springframework.samples.petclinic.repository.RestaurantTableRepository;
import org.springframework.transaction.annotation.Transactional;

public class RestaurantReservationService {
	private  RestaurantReservationRepository restaurantReservationrepo; 

	@Autowired
	public RestaurantReservationService(RestaurantReservationRepository restaurantReservationrepo) {
		this.restaurantReservationrepo = restaurantReservationrepo;
	}
	
	@Transactional
	public int restaurantReservationCount() {
		return (int)restaurantReservationrepo.count();
	}
	
	@Transactional
	public Iterable<RestaurantReservation> findAll() {
		return restaurantReservationrepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<RestaurantReservation> findRestaurantReservationId(int id){ 
		return restaurantReservationrepo.findById(id);
	}

	@Transactional
	public  void save(RestaurantReservation restaurantReservation) {   
		restaurantReservationrepo.save(restaurantReservation);
	}

	public  void delete(RestaurantReservation restaurantReservation) { 
		restaurantReservationrepo.delete(restaurantReservation);
	}
	
	@Transactional
	public Collection<RestaurantReservation> findRestaurantReservations() {
		return restaurantReservationrepo.findRestaurantReservations();
	}
}
