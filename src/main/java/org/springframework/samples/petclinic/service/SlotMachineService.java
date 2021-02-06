package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.repository.SlotMachineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SlotMachineService {
	
	private  SlotMachineRepository slotMachineRepo; 
	
	@Autowired
	public SlotMachineService(SlotMachineRepository slotMachineRepo) {
		this.slotMachineRepo = slotMachineRepo;
	}
	
	@Transactional
	public int slotMachineCount() {
		return (int)slotMachineRepo.count();
	}
	
	@Transactional
	public Iterable<SlotMachine> findAll() {
		return slotMachineRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<SlotMachine> findSlotMachineById(int id){ 
		return slotMachineRepo.findById(id);
	}

	@Transactional
	public  void save(SlotMachine slotMachine) {   
		slotMachineRepo.save(slotMachine);
	}

	public  void delete(SlotMachine slotMachine) { 
		slotMachineRepo.delete(slotMachine);
	}
	
	@Transactional
	public Collection<Status> findStatus() {
		log.info("Loading status from DB");
		return slotMachineRepo.findStatus();
	}
	
	@Transactional
	public Collection<Slotgame> findSlotgames() {
		log.info("Loading slot games from DB");
		return slotMachineRepo.findSlotgames();
	}
	
	@Transactional
	public Collection<SlotGain> findGains() {
		log.info("Loading gains from DB");
		return slotMachineRepo.findGains();
	}
	
}
