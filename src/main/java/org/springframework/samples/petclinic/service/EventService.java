package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Artist;

import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.repository.EventRepository;
import org.springframework.samples.petclinic.repository.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return eventRepo.findShowtypes();
    }
	public Collection<Artist> findArtists() throws DataAccessException{
        // TODO Auto-generated method stub
        return eventRepo.findArtists();
    }


}
