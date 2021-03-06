package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReservationRepository extends CrudRepository<RestaurantReservation, Integer>{
	@Query("SELECT restaurantreservation FROM RestaurantReservation restaurantreservation ORDER BY restaurantreservation.id")
	List<RestaurantReservation> findRestaurantReservations() throws DataAccessException;
	
	@Query("SELECT timeinterval FROM TimeInterval timeinterval ORDER BY timeinterval.id")
	List<TimeInterval> findTimeIntervals() throws DataAccessException;
	
	@Query("SELECT DISTINCT date FROM RestaurantReservation")
	public List<LocalDate> findAllDates();
	
	@Query("SELECT restaurantreservation FROM RestaurantReservation restaurantreservation where restaurantreservation.date = :date ORDER BY restaurantreservation.timeInterval.id")
	public List<RestaurantReservation> findRestaurantReservationsByDate(@Param("date") LocalDate date);
	
	@Query("SELECT restauranttable FROM RestaurantTable restauranttable")
	public List<RestaurantTable> findRestaurantTables();
	
	@Query("SELECT DISTINCT c FROM Client c WHERE c.user.username LIKE :user%")
	public Client findClientFromUsername(@Param("user")String user) throws DataAccessException;

	@Query("SELECT DISTINCT c FROM Client c")
	public Collection<Client> findClients();
	
	@Query("SELECT  auth FROM Authority auth WHERE auth.user.username LIKE :username")
	public Authority getAuthority(@Param("username")String username) throws DataAccessException;
	
	@Query("SELECT DISTINCT rreservation FROM RestaurantReservation rreservation WHERE rreservation.client.dni = :dni ORDER BY rreservation.date")
	List<RestaurantReservation> findRestaurantReservationForClient(@Param("dni") String dni) throws DataAccessException;
	
}
