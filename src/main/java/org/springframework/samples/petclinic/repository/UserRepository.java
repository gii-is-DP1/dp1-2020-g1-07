package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
	@Query("SELECT employee FROM Employee employee ORDER BY employee.id")
	List<Employee> findEmployees() throws DataAccessException;
	
	@Query("SELECT client FROM Client client ORDER BY client.id")
	List<Client> findClients() throws DataAccessException;
}
