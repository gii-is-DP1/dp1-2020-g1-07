package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ShowReservation;
import org.springframework.samples.petclinic.repository.ShowReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShowReservationService {
private  ShowReservationRepository showresRepo;
	
	@Autowired
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
}
