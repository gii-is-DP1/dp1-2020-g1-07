package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.SkillLevel;
import org.springframework.samples.petclinic.repository.CasinotableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CasinotableService {
	@Autowired
	private  CasinotableRepository castabRepo;
	
	@Transactional
	public int casinoTableCount() {
		return (int)castabRepo.count();
	}
	
	@Transactional
	public Iterable<Casinotable> findAll() {
		return castabRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Casinotable> findCasinotableById(int id){
		return castabRepo.findById(id);
	}

	@Transactional
	public  void save(Casinotable casinotable) {
		castabRepo.save(casinotable);
	}

	public  void delete(Casinotable casinotable) {
		castabRepo.delete(casinotable);
	}
	public Collection<SkillLevel> findSkillLevels() throws DataAccessException{
        // TODO Auto-generated method stub
        return castabRepo.findSkillLevels();
    }
}
