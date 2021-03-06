package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.model.Stage;


public interface EventRepository extends CrudRepository<Event, Integer>{

	@Query("SELECT stype FROM ShowType stype ORDER BY stype.id")
	List<ShowType> findShowtypes() throws DataAccessException;

	@Query("SELECT artist FROM Artist artist ORDER BY artist.id")
	List<Artist> findArtists() throws DataAccessException;
	
	@Query("SELECT DISTINCT date FROM Event")
	public List<LocalDate> findAllDates();

	@Query("SELECT event FROM Event event where event.date = :date ORDER BY event.id")
	public List<Event> findEventsByDate(@Param("date") LocalDate date);

	//@Query("SELECT stage FROM Stage stage where stage.event_id.id = :id")
	//public Stage findStageForEvent(@Param("id") Integer id);
	
	@Query("SELECT stage FROM Stage stage ORDER BY stage.id")
	List<Stage> findStages() throws DataAccessException;
	
	@Query("SELECT event FROM Event event WHERE event.name LIKE :name ORDER BY event.name")
	Optional<Event> findEventByName(@Param("name")String name);
}
