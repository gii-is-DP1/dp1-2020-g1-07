package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.stereotype.Repository;

@Repository("employeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

	@Query("SELECT DISTINCT e FROM Employee e WHERE e.dni LIKE :dni%")
	Employee findEmployeeForDni(@Param("dni") String dni) throws DataAccessException;
	
}
