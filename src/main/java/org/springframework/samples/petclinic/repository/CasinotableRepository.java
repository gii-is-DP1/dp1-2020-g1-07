package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.ClientGain;
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
	
	@Query("SELECT casinotable FROM Casinotable casinotable ORDER BY casinotable.id")
	public List<Casinotable> findCasinoTables();
	
	@Query("SELECT DISTINCT date FROM Casinotable")
	public List<LocalDate> findAllDates();

	@Query("SELECT casinotable FROM Casinotable casinotable where casinotable.date = :date ORDER BY casinotable.id")
	public List<Casinotable> findCasinoTablesByDate(@Param("date") LocalDate date);

	@Query(value="SELECT CLIENT_GAINS.AMOUNT FROM CASINOTABLE JOIN CLIENT_GAINS ON CASINOTABLE.ID=CLIENT_GAINS.TABLEID WHERE CLIENT_GAINS.TABLEID=:tableId",nativeQuery = true)
    public List<Integer> findGainsByTableId(@Param("tableId") Integer tableId);
	
	@Query("SELECT clientGain FROM ClientGain clientGain ORDER BY clientGain.id")
	List<ClientGain> findGains() throws DataAccessException;
}
