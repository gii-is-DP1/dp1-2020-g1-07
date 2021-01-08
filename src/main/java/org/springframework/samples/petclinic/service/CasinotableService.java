package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.repository.CasinotableRepository;
import org.springframework.samples.petclinic.repository.StageRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CasinotableService {
	@Autowired
	private  CasinotableRepository castabRepo;
	@Autowired
	public CasinotableService(CasinotableRepository castabRepo) {
		this.castabRepo = castabRepo;
	}		
	
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
	public  void save(Casinotable casinotable) throws DataAccessException {
		castabRepo.save(casinotable);
		
	}
	@Transactional
	public Collection<LocalDate> findAllDates() {
		return castabRepo.findAllDates();
	}
	public  void delete(Casinotable casinotable) {
		castabRepo.delete(casinotable);
	}
	public Collection<Skill> findSkills() throws DataAccessException{
        // TODO Auto-generated method stub
        return castabRepo.findSkills();
    }
	public Collection<GameType> findGameTypes() throws DataAccessException{
        // TODO Auto-generated method stub
        return castabRepo.findGameTypes();
    }

	public Collection<Game> findGames() throws DataAccessException{
		// TODO Auto-generated method stub
		return castabRepo.findGames();
	}
	public Collection<Game> findGamesByGameType(int id) throws DataAccessException {
        return castabRepo.findGamesByGameType(id);
    }
	public Collection<Casinotable> findCasinoTables(){
        return castabRepo.findCasinoTables();
    }
	public Collection<Casinotable> findCasinoTablesByDate(LocalDate date) {
		return castabRepo.findCasinoTablesByDate(date);
	}
}
