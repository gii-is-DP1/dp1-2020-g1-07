package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.stereotype.Repository;

@Repository("cookRepository")
public interface CookRepository extends CrudRepository<Cook, Integer>{

}
