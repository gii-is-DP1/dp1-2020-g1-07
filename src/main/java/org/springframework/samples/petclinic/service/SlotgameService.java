package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.repository.SlotgameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlotgameService {
	
	@Autowired
	private  SlotgameRepository slotGameRepo; 
	
	@Transactional
	public int slotGameCount() {
		return (int)slotGameRepo.count();
	}
	
	@Transactional
	public Iterable<Slotgame> findAll() {
		return slotGameRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Slotgame> findSlotgameById(int id){ 
		return slotGameRepo.findById(id);
	}

	@Transactional
	public  void save(Slotgame slotgame) {   
		slotGameRepo.save(slotgame);
	}

	public  void delete(Slotgame slotgame) { 
		slotGameRepo.delete(slotgame);
	}
	
	

}
