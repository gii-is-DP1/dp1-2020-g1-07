package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;

public interface ClientGainRepository extends CrudRepository<ClientGain,Integer>{

	@Query("SELECT DISTINCT date FROM ClientGain")
	public List<LocalDate> findAllDates();
	
	@Query("SELECT user FROM User user ORDER BY user.dni")
	List<User> findUsers() throws DataAccessException;
	
	@Query("SELECT dni FROM Client client ORDER BY client.id")
	List<String> findClients() throws DataAccessException;
	
	@Query("SELECT name FROM Game game ORDER BY game.name")
	List<String> findGames() throws DataAccessException;
}
