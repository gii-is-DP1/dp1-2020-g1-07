package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CasinoReservation;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;

public interface CasinoReservationRepository extends CrudRepository<CasinoReservation,Integer> {

	@Query("SELECT game FROM Game game ORDER BY game.id ")
	List<Game> findGames()throws DataAccessException;
	
	@Query("SELECT casinoreservation FROM Casinoreservation casinoreservation ORDER BY casinoreservation.id")
	public List<CasinoReservation> findCasinoreservations();
	
	@Query("SELECT casinotable FROM Casinotable casinotable ORDER BY casinotable.id")
	public List<Casinotable> findCasinoTables();
	
	
}
