package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;

public interface SlotMachineRepository  extends CrudRepository<SlotMachine, Integer>{
	
	@Query("SELECT status FROM Status status ORDER BY status.id")
	List<Status> findStatus() throws DataAccessException;
	
	@Query("SELECT slotgame FROM Slotgame slotgame ORDER BY slotgame.id")
	List<Slotgame> findSlotgames() throws DataAccessException;
	
	@Query("SELECT slotgain FROM SlotGain slotgain ORDER BY slotgain.id")
	List<SlotGain> findGains() throws DataAccessException;

}
