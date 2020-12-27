package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClientGainRepository;
import org.springframework.samples.petclinic.util.UserUtils;
import org.springframework.samples.petclinic.util.Week;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientGainService {
	
	private  ClientGainRepository cgainRepo;
	
	@Autowired
	public ClientGainService(ClientGainRepository cgainRepo) {
		this.cgainRepo = cgainRepo;
	}
	
	@Transactional
	public int cgainCount() {
		return (int)cgainRepo.count();
	}

	@Transactional
	public Iterable<ClientGain> findAll() {
		return cgainRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<ClientGain> findClientGainById(int id){ 
		return cgainRepo.findById(id);
	}
	
	@Transactional
	public  void save(ClientGain cgain) {
		cgainRepo.save(cgain);
	}

	public  void delete(ClientGain cgain) { 
		cgainRepo.delete(cgain);
	}
	
	
	@Transactional
	public String findClientByUsername(String username) {
		log.info("List of users: " + cgainRepo.findUsers());
		return cgainRepo.findUsers().stream()
				.filter(x -> x.getUsername().equals(username))
				.findFirst().get().getDni();
	}

	@Transactional
	public SortedSet<Week> findWeeksForUser() {
		String dni = this.findClientByUsername(UserUtils.getUser());
		Collection<LocalDate> dates = cgainRepo.findDatesForClient(dni);
		SortedSet<Week> weeks = new TreeSet<>();
		for (LocalDate day : dates) {
			Week w = new Week(day);
			weeks.add(w);
		}
		return weeks;
	}
	
	public List<ClientGain> findClientGainsForWeek(Week week, String dni) {
		return cgainRepo.findClientGainsForWeek(dni, 
				week.getMonday(), week.getSunday());
	}
	
	public Collection<User> findUsers() throws DataAccessException{
		return cgainRepo.findUsers();
	}
	
	public Collection<Client> findClients() throws DataAccessException{
		return cgainRepo.findClients();
	}
	
	public Collection<Game> findGames() throws DataAccessException{
		return cgainRepo.findGames();
	}
}
