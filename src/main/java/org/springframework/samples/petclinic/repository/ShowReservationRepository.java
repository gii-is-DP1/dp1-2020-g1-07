package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.stereotype.Repository;

@Repository("showReservationRepository")
public interface ShowReservationRepository extends CrudRepository<ShowReservation, Integer>{

	@Query("SELECT DISTINCT e FROM Event e WHERE e.date >= :today% ORDER BY e.date")
	List<Event> findAvailableShows(@Param("date") LocalDate today) throws DataAccessException;
	
	@Query("SELECT DISTINCT s FROM ShowReservation s WHERE s.event.date >= :today% AND s.client = :client% ORDER BY s.event.date")
	List<ShowReservation> findReservationsForUser(@Param("date") LocalDate today, Client client) throws DataAccessException;
	
	@Query("SELECT DISTINCT c FROM Client c WHERE c.user.username = :user%")
	Client findClientFromUsername(@Param("user") String user) throws DataAccessException;
	
	@Query("SELECT SUM(r.seats) FROM ShowReservation r WHERE r.event.id LIKE :id%")
	Integer findReservedSeats(@Param("id") Integer id) throws DataAccessException;
	
	@Query("SELECT e.stage.capacity FROM Event s WHERE e.id LIKE :id%")
	Integer findTotalSeats(@Param("id") Integer id) throws DataAccessException;
}
