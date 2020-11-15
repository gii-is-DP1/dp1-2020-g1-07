package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cook;

public interface CookRepository extends CrudRepository<Cook, String>{

}
