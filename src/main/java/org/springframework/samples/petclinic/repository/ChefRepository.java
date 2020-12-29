package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Chef;
import org.springframework.stereotype.Repository;

@Repository("chefRepository")
public interface ChefRepository extends CrudRepository<Chef, Integer>{

}
