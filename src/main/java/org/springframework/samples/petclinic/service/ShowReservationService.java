package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.repository.ShowReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShowReservationService {
	
	@Autowired
	private  ShowReservationRepository showresRepo;
	
	public ShowReservationService(ShowReservationRepository showresRepo) {
		this.showresRepo = showresRepo;
	}
	
	@Transactional
	public int showresCount() {
		return (int)showresRepo.count();
	}

	@Transactional
	public Iterable<ShowReservation> findAll() {
		return showresRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<ShowReservation> findShowReservationById(int id){ 
		return showresRepo.findById(id);
	}
	
	@Transactional
	public  void save(ShowReservation showres) {
		showresRepo.save(showres);
	}

	public  void delete(ShowReservation showres) { 
		showresRepo.delete(showres);
	}
	
	public Collection<Event> findAvailableShows() throws DataAccessException{
		log.info("Loading shows available to book seats");
		return showresRepo.findAvailableShows(LocalDate.now());
	}
	
	public Collection<ShowReservation> findReservationsForUser(Client client) throws DataAccessException{
		log.info("Loading show reservations for client: " + client.getDni());
		return showresRepo.findReservationsForUser(LocalDate.now(), client.getId());
	}
	
	public Client findClientFromUsername(String username) throws DataAccessException{
		log.info("Loading logged client");
		return showresRepo.findClientFromUsername(username);
	}
	
	public Integer findAvailableSeats(Integer eventId) throws DataAccessException{
		log.info("Loading available seats from DB for event: " + eventId);
		return showresRepo.findTotalSeats(eventId) - showresRepo.findReservedSeats(eventId);
	}
	public List<ShowReservation> findShowReservationForClient(String dni) {
		log.info("Loading show reservation from DB for client: " + dni);
		return showresRepo.findShowReservationForClient(dni);
	}
	
}
