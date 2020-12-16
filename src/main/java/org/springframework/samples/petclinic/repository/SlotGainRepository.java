package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;

public interface SlotGainRepository extends CrudRepository<SlotGain, Integer> {

	@Query("SELECT slotMachine FROM SlotMachine slotMachine ORDER BY slotMachine.id")
	List<SlotMachine> findSlotMachines() throws DataAccessException;
	
}
