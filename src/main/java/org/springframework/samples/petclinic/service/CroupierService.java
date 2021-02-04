package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.samples.petclinic.repository.CroupierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CroupierService {
	@Autowired
	private  CroupierRepository croupierRep;
	
	@Autowired
	public CroupierService(CroupierRepository croupierRep) {
		this.croupierRep = croupierRep;
	}
	
	@Transactional
	public int croupierCount() {
		return (int)croupierRep.count();
	}
	
	@Transactional
	public Iterable<Croupier> findAll() {
		return croupierRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Croupier> findCroupierById(int id){ 
		return croupierRep.findById(id);
	}

	@Transactional
	public  void save(Croupier croupier) {   
		croupierRep.save(croupier);
	}

	public  void delete(Croupier croupier) { 
		croupierRep.delete(croupier);
	}
	
	public Collection<Casinotable> findCasinotables()throws DataAccessException{
	 log.info("Loading Casinotables from DB");
	  return croupierRep.findCasinoTables();
	}
}
