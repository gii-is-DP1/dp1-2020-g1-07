package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.repository.SlotGainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SlotGainService {
	
	private  SlotGainRepository slotGainRepo; 
	
	@Autowired
	public SlotGainService(SlotGainRepository slotGainRepo) {
		this.slotGainRepo = slotGainRepo;
	}
	
	@Transactional
	public int slotGainCount() {
		return (int)slotGainRepo.count();
	}
	
	@Transactional
	public Iterable<SlotGain> findAll() {
		return slotGainRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<SlotGain> findSlotGainById(int id){ 
		return slotGainRepo.findById(id);
	}

	@Transactional
	public  void save(SlotGain slotGain) {   
		slotGainRepo.save(slotGain);
	}

	public  void delete(SlotGain slotGain) { 
		slotGainRepo.delete(slotGain);
	}
	
	@Transactional
	public Collection<SlotMachine> findSlotMachines() {
		log.info("Loading list of slot machines from DB");
		return slotGainRepo.findSlotMachines();
	}
	@Transactional
	public Collection<SlotGain> findSlotGainByDate(LocalDate date) {
		log.info("Loading list of slot gains for a date:" + date);
		return slotGainRepo.findSlotGainByDate(date);
	}
	@Transactional
	public Collection<Integer> findGainsBySlotId(Integer id) {
		log.info("Loading list of gains for a slot ID:" + id);
        return slotGainRepo.findGainsBySlotId(id);
    }
	@Transactional
	public Collection<LocalDate> findAllDates() {
		return slotGainRepo.findAllDates();
	}
}
