package org.springframework.samples.petclinic.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Administrator;
import org.springframework.stereotype.Repository;

@Repository("administratorRepository")
public interface AdministratorRepository extends CrudRepository<Administrator, Integer>{

}
