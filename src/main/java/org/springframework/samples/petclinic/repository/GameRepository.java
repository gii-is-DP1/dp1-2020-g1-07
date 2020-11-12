package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;

public interface GameRepository extends CrudRepository<Game, Integer>{
	
	@Query("SELECT gtype FROM GameType gtype ORDER BY gtype.id")
    List<GameType> findGameTypes() throws DataAccessException;
	
}
