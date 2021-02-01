package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.stereotype.Repository;

@Repository("showReservationRepository")
public interface ShowReservationRepository extends CrudRepository<ShowReservation, Integer>{

	@Query("SELECT DISTINCT r FROM ShowReservation r WHERE r.event.date LIKE :date% ORDER BY r.event.name")
	List<ShowReservation> findShowresForDay(@Param("date") LocalDate date) throws DataAccessException;
	
	@Query("SELECT SUM(r.seats) FROM ShowReservation r WHERE r.event.id LIKE :id%")
	Integer findReservedSeats(@Param("id") Integer id) throws DataAccessException;
	
	@Query("SELECT e.stage.capacity FROM Event s WHERE e.id LIKE :id%")
	Integer findTotalSeats(@Param("id") Integer id) throws DataAccessException;
}
