package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.stereotype.Repository;


public interface CasinotableRepository extends CrudRepository<Casinotable, Integer>{
	
	@Query("SELECT skill FROM Skill skill ORDER BY skill.id")
    List<Skill> findSkills() throws DataAccessException;
	
	@Query("SELECT gtype FROM GameType gtype ORDER BY gtype.id")
    List<GameType> findGameTypes() throws DataAccessException;

	@Query("SELECT game FROM Game game ORDER BY game.id ")
	List<Game> findGames()throws DataAccessException;

	@Query("SELECT game FROM Game game WHERE game.gametype.id = :id ORDER BY game.id")
	public List<Game> findGamesByGameType(@Param("id") int id);
	
	@Query("SELECT casinotable FROM Casinotable casinotable ORDER BY casinotable.id")
	public List<Casinotable> findCasinoTables();
	
	@Query("SELECT DISTINCT date FROM Casinotable")
	public List<LocalDate> findAllDates();

	@Query("SELECT casinotable FROM Casinotable casinotable where casinotable.date = :date ORDER BY casinotable.id")
	public List<Casinotable> findCasinoTablesByDate(@Param("date") LocalDate date);

}
