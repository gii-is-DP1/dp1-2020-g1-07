package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, String>{
	
	@Query("SELECT e FROM Employee e WHERE NOT EXISTS"
			+ "(SELECT a FROM Administrator a WHERE a.id LIKE e.id) ORDER BY e.id")
	List<Employee> findEmployees() throws DataAccessException;
	
	@Query("SELECT a FROM Administrator a ORDER BY a.id")
	List<Administrator> findAdmins() throws DataAccessException;
	
	@Query("SELECT c FROM Client c ORDER BY c.dni")
	List<Client> findClients() throws DataAccessException;
	
	@Query("SELECT e FROM Employee e WHERE EXISTS"
			+ "(SELECT u FROM User u WHERE e.user.username LIKE u.username)"
			+ "and NOT EXISTS(SELECT a FROM Administrator a WHERE a.id LIKE e.id) ORDER BY e.id ")
	List<Employee> findEmployeesWithAccount() throws DataAccessException;
	
	@Query("SELECT a FROM Administrator a WHERE EXISTS"
			+ "(SELECT u FROM User u WHERE a.user.username LIKE u.username) ORDER BY a.id")
	List<Administrator> findAdminsWithAccount() throws DataAccessException;
	
	@Query("SELECT c FROM Client c WHERE EXISTS"
			+ "(SELECT u FROM User u WHERE c.user.username LIKE u.username) ORDER BY c.id")
	List<Client> findClientsWithAccount() throws DataAccessException;
	
	@Query("SELECT DISTINCT a FROM Authority a WHERE a.user.username LIKE :username ORDER BY a.id")
	Optional<Collection<Authority>> findAuthoritiesForUser(@Param("username") String username) throws DataAccessException;
	
	@Query("SELECT c FROM Client c WHERE c.user.username LIKE :username")
	Client findClientForUsername(@Param("username") String username) throws DataAccessException;
	
	@Query("SELECT e FROM Employee e WHERE e.user.username LIKE :username")
	Employee findEmployeeForUsername(@Param("username") String username) throws DataAccessException;
	
	
}
