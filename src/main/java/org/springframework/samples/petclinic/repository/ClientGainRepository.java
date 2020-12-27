package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Repository;

@Repository("clientGainRepository")
public interface ClientGainRepository extends CrudRepository<ClientGain,Integer>{

	@Query("SELECT DISTINCT date FROM ClientGain cgain WHERE cgain.client.dni LIKE :dni% ORDER BY cgain.date")
	List<LocalDate> findDatesForClient(@Param("dni") String dni) throws DataAccessException;
	
	@Query("SELECT DISTINCT cgain FROM ClientGain cgain WHERE cgain.date >= :start and "
			+ "cgain.date <= :end and cgain.client.dni = :dni ORDER BY cgain.date")
	List<ClientGain> findClientGainsForWeek(@Param("dni") String dni, @Param("start") LocalDate monday,
			@Param("end") LocalDate sunday) throws DataAccessException;
	
	@Query("SELECT DISTINCT user FROM User user ORDER BY user.dni")
	List<User> findUsers() throws DataAccessException;
	
	@Query("SELECT client FROM Client client ORDER BY client.dni")
	List<Client> findClients() throws DataAccessException;
	
	@Query("SELECT game FROM Game game ORDER BY game.name")
	List<Game> findGames() throws DataAccessException;
}
