package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.repository.DishRepository;
import org.springframework.samples.petclinic.repository.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class StageService {
	@Autowired
	private  StageRepository stgrepo;
	
	@Autowired
	public StageService(StageRepository stgrepo) {
		this.stgrepo = stgrepo;
	}		
	
	@Transactional
	public int stageCount() {
		return (int)stgrepo.count();
	}
	
	@Transactional
	public Iterable<Stage> findAll() {
		return stgrepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Stage> findStagebyId(int id){
		return stgrepo.findById(id);
	}

	@Transactional
	public  void save(Stage stage) throws DataAccessException {
		stgrepo.save(stage);
		
	}

	public  void delete(Stage stage) {
		stgrepo.delete(stage);
	}
	public Collection<Event> findEvents() throws DataAccessException{
        // TODO Auto-generated method stub
		log.info("Loading events from DB");
        return stgrepo.findEvents();
    }
	
	
}
