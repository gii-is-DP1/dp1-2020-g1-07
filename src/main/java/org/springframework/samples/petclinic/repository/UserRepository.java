package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, String>{
	
	@Query("SELECT e FROM Employee e ORDER BY e.id")
	List<Employee> findEmployees() throws DataAccessException;
	
	@Query("SELECT c FROM Client c ORDER BY c.id")
	List<Client> findClients() throws DataAccessException;
}
