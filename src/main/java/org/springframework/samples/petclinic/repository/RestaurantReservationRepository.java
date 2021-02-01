package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReservationRepository extends CrudRepository<RestaurantReservation, Integer>{
	@Query("SELECT restaurantreservation FROM RestaurantReservation restaurantreservation ORDER BY restaurantreservation.id")
	List<RestaurantReservation> findRestaurantReservations() throws DataAccessException;
	
	@Query("SELECT timeinterval FROM TimeInterval timeinterval ORDER BY timeinterval.id")
	List<TimeInterval> findTimeIntervals() throws DataAccessException;
}
