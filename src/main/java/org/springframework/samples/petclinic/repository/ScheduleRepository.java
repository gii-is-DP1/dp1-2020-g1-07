package org.springframework.samples.petclinic.repository;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer>{

	@Query("SELECT shift FROM Shift shift ORDER BY shift.id")
    List<Shift> findShifts() throws DataAccessException;
	
	@Query("SELECT employee.dni FROM Employee employee ORDER BY employee.dni")
	List<String> findEmployeeDni() throws DataAccessException;
	
	@Query("SELECT employee FROM Employee employee ORDER BY employee.id")
	List<Employee> findEmployees() throws DataAccessException;
	
	@Query("SELECT DISTINCT e FROM Employee e WHERE e.user.username LIKE :username")
	Employee findEmployeeByUsername(@Param("username") String username) throws DataAccessException;
}
