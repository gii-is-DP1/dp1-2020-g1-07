package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.repository.CasinotableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CasinotableService {
	@Autowired
	private  CasinotableRepository castabRepo; ///Cambiado a static aunque no viene en el video
	
	@Transactional
	public int casinoTableCount() {
		return (int)castabRepo.count();
	}
	
	@Transactional
	public Iterable<Casinotable> findAll() {
		return castabRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Casinotable> findCasinotableById(int id){ ///Cambiado a static aunque no viene en el video
		return castabRepo.findById(id);
	}

	@Transactional
	public  void save(Casinotable casinotable) {   ///Cambiado a static aunque no viene en el video
		castabRepo.save(casinotable);
	}

	public  void delete(Casinotable casinotable) { ///Cambiado a static aunque no viene en el video
		castabRepo.delete(casinotable);
	}

}
