package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.repository.SlotGainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlotGainService {
	@Autowired
	private  SlotGainRepository slotGainRepo; 
	
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
}
