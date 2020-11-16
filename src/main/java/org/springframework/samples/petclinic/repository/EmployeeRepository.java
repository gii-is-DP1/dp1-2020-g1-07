package org.springframework.samples.petclinic.repository;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Shift;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

	@Query("SELECT shift FROM Shift shift ORDER BY shift.id")
    List<Shift> findShifts() throws DataAccessException;
}
