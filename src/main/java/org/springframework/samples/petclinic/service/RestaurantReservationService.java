package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.repository.RestaurantReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
		log.info("Loading time intervals from DB");
		return restaurantReservationrepo.findTimeIntervals();
	}
	
	@Transactional
	public Collection<LocalDate> findAllDates() {
		log.info("Loading all dates from DB");
		return restaurantReservationrepo.findAllDates();
	}
	
	public Collection<RestaurantReservation> findRestaurantReservationsByDate(LocalDate date) {
		// TODO Auto-generated method stub
		log.info("Loading restaurant reservations from DB with date: " + date);
		return restaurantReservationrepo.findRestaurantReservationsByDate(date);
	}
	
	@Transactional
	public Collection<RestaurantTable> findRestaurantTables() {
		log.info("Loading restaurant tables from DB");
		return restaurantReservationrepo.findRestaurantTables();
	}

	public Client findClientFromUsername(String username) {
		// TODO Auto-generated method stub
		log.info("Loading client from DB with username: " + username);
		return restaurantReservationrepo.findClientFromUsername(username);
	}

	public Collection<Client> findClients() {
		// TODO Auto-generated method stub
		log.info("Loading clients from DB");
		return restaurantReservationrepo.findClients();
	}

	public Authority getAuthority(String username) {
		// TODO Auto-generated method stub
		log.info("Getting authority with the username:" + username);
		return restaurantReservationrepo.getAuthority(username);
	}
}
