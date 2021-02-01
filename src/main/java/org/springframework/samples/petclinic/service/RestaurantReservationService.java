package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.repository.RestaurantReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
	
	public Collection<TimeInterval> findTimeIntervals() throws DataAccessException{
		// TODO Auto-generated method stub
		return restaurantReservationrepo.findTimeIntervals();
	}
	
	@Transactional
	public Collection<LocalDate> findAllDates() {
		return restaurantReservationrepo.findAllDates();
	}
}
