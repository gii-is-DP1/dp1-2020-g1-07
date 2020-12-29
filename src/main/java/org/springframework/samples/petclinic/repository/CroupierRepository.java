package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.stereotype.Repository;

@Repository("croupierRepository")
public interface CroupierRepository extends CrudRepository<Croupier, Integer>{

}
