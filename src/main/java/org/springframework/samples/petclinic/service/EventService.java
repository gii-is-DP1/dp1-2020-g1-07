package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdk.internal.jline.internal.Log;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class EventService {
	@Autowired
	private  EventRepository eventRepo;
	
	@Autowired
	public EventService(EventRepository eventRepo) {
		this.eventRepo = eventRepo;
	}	
	@Transactional
	public int eventCount() {
		return (int)eventRepo.count();
	}
	
	@Transactional
	public Iterable<Event> findAll() {
		return eventRepo.findAll();
	}
	
	@Transactional
	public Collection<LocalDate> findAllDates() {
		log.info("Loading dates from DB");
		return eventRepo.findAllDates();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Event> findEventbyId(int id){
		return eventRepo.findById(id);
	}

	@Transactional
	public  void save(Event event) throws DataAccessException {
		eventRepo.save(event);	
	}
	public  void delete(Event event) {
		eventRepo.delete(event);
	}
	public Collection<ShowType> findShowTypes() throws DataAccessException{
        // TODO Auto-generated method stub
		log.info("Loading showtypes from DB");
        return eventRepo.findShowtypes();
    }
	public Collection<Artist> findArtists() throws DataAccessException{
        // TODO Auto-generated method stub
		log.info("Loading artists from DB");
        return eventRepo.findArtists();
    }
	
	public Collection<Event> findEventsByDate(LocalDate date) {
		// TODO Auto-generated method stub
		log.info("Loading events from DB for a date:" + date);
		return eventRepo.findEventsByDate(date);
	}
	/*public Stage findStageForEvent(Integer id) {
		// TODO Auto-generated method stub
		return eventRepo.findStageForEvent(id);
	}*/
	public Collection<Stage> findStages(){
		log.info("Loading stages from DB");
		return eventRepo.findStages();
	}


}
