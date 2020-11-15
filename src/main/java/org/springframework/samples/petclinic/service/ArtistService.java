package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.repository.ArtistRepository;
import org.springframework.transaction.annotation.Transactional;

public class ArtistService {
	@Autowired
	private  ArtistRepository artistRep; 
	
	@Transactional
	public int artistCount() {
		return (int)artistRep.count();
	}
	
	@Transactional
	public Iterable<Artist> findAll() {
		return artistRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Artist> findArtistById(String id){ 
		return artistRep.findById(id);
	}

	@Transactional
	public  void save(Artist artist) {   
		artistRep.save(artist);
	}

	public  void delete(Artist artist) { 
		artistRep.delete(artist);
	}
}
