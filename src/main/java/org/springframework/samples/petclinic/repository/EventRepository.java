package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;


public interface EventRepository extends CrudRepository<Event, Integer>{

	@Query("SELECT showtype FROM Showtype showtype ORDER BY showtype.id")
	List<ShowType> findShowtypes() throws DataAccessException;

	@Query("SELECT artist FROM Artist artist ORDER BY artist.id")
	List<Artist> findArtists() throws DataAccessException;
	
}
