package org.springframework.samples.petclinic.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.stereotype.Repository;

@Repository("employeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

}
