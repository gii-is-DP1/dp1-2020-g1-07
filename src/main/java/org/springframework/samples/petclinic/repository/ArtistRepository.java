package org.springframework.samples.petclinic.repository;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.stereotype.Repository;

@Repository("artistRepository")
public interface ArtistRepository extends CrudRepository<Artist, Integer>{
	
	@Query("SELECT artist.acts FROM Artist artist WHERE artist.id=:artistId")
	public List<Event> findActedEvents(@Param("artistId")int artistId) throws DataAccessException;
	
	@Query("SELECT event FROM Event event ORDER BY event.id")
	public List<Event> findEvents() throws DataAccessException;

}
