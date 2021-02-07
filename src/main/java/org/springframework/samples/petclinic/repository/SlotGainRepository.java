package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;

public interface SlotGainRepository extends CrudRepository<SlotGain, Integer> {

	@Query("SELECT slotMachine FROM SlotMachine slotMachine ORDER BY slotMachine.id")
	List<SlotMachine> findSlotMachines() throws DataAccessException;
	
	@Query("SELECT slotgains FROM  SlotGain slotgains  where slotgains.date = :date ")
	public List<SlotGain> findSlotGainByDate(@Param("date") LocalDate date);
	
	@Query("SELECT DISTINCT date FROM SlotGain")
	public List<LocalDate> findAllDates();
	
	@Query(value="SELECT AMOUNT FROM SLOTGAINS SLOTGAINS WHERE SLOT_MACHINE_ID=:slotId",nativeQuery = true)
    public List<Integer> findGainsBySlotId(@Param("slotId") Integer slotId);
}
