package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.stereotype.Repository;

@Repository("authorityRepository")
public interface AuthorityRepository extends CrudRepository<Authority, Integer>{

}
