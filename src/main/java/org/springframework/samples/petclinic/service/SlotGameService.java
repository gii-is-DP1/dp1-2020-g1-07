package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGame;
import org.springframework.samples.petclinic.repository.SlotGameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlotGameService {
	
	@Autowired
	private  SlotGameRepository slotGameRepo; 
	
	@Transactional
	public int slotGameCount() {
		return (int)slotGameRepo.count();
	}
	
	@Transactional
	public Iterable<SlotGame> findAll() {
		return slotGameRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<SlotGame> findSlotGameById(int id){ 
		return slotGameRepo.findById(id);
	}

	@Transactional
	public  void save(SlotGame slotGame) {   
		slotGameRepo.save(slotGame);
	}

	public  void delete(SlotGame slotGame) { 
		slotGameRepo.delete(slotGame);
	}

}
