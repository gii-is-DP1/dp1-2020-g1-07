package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.repository.ArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistService {

	private  ArtistRepository artistRep;
	
	@Autowired
	public ArtistService(ArtistRepository artistRep) {
		this.artistRep = artistRep;
	}
	
	@Transactional
	public int artistCount() {
		return (int)artistRep.count();
	}
	
	@Transactional
	public Iterable<Artist> findAll() {
		return artistRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Artist> findArtistById(int id){ 
		return artistRep.findById(id);
	}

	@Transactional
	public  void save(Artist artist) {   
		artistRep.save(artist);
	}

	public  void delete(Artist artist) { 
		artistRep.delete(artist);
	}
	
	public List<Event> findActedEvents(int artistId) {
		// TODO Auto-generated method stub
		return artistRep.findActedEvents(artistId);
	}
	
	public List<Event> findEvents() {
		// TODO Auto-generated method stub
		return artistRep.findEvents();
	}
}
