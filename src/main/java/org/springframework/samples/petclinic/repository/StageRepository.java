package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Stage;

public interface StageRepository extends CrudRepository<Stage, Integer>{

	@Query("SELECT event FROM Event event ORDER BY event.id")
	List<Event> findEvents() throws DataAccessException;
	
}
