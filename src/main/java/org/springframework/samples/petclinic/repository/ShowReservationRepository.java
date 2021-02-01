package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.ShowReservation;

public interface ShowReservationRepository extends CrudRepository<ShowReservation, Integer>{

	
}
