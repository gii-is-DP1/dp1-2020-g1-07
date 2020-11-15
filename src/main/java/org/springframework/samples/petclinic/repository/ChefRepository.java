package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, String>{

}
