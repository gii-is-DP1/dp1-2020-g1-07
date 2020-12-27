package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;

public interface CasinotableRepository extends CrudRepository<Casinotable, Integer>{
	
	@Query("SELECT skill FROM Skill skill ORDER BY skill.id")
    List<Skill> findSkills() throws DataAccessException;
	
	@Query("SELECT gtype FROM GameType gtype ORDER BY gtype.id")
    List<GameType> findGameTypes() throws DataAccessException;

	@Query("SELECT game FROM Game game ORDER BY game.id ")
	List<Game> findGames()throws DataAccessException;

	@Query("SELECT game FROM Game game WHERE game.gametype.id = :id ORDER BY game.id")
	public List<Game> findGamesByGameType(@Param("id") int id);
	


}
