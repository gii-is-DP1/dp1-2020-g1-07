package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.SlotGame;

public interface SlotGameRepository extends CrudRepository<SlotGame, Integer> {

}
