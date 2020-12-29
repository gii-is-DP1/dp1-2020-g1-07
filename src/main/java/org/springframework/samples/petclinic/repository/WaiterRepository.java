package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.stereotype.Repository;

@Repository("waiterRepository")
public interface WaiterRepository extends CrudRepository<Waiter, Integer>{

}
