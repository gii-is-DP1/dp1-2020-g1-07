package org.springframework.samples.petclinic.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClientGainRepository;
import org.springframework.samples.petclinic.util.Week;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientGainService {
	@Autowired
	private  ClientGainRepository cgainRepo;
	
	@Transactional
	public int cgainCount() {
		return (int)cgainRepo.count();
	}
	
	@Transactional
	public Iterable<ClientGain> findAll() {
		return cgainRepo.findAll();
	}
	
	@Transactional
	public SortedSet<Week> findAllWeeks() {
		Collection<LocalDate> dates = cgainRepo.findAllDates();
		SortedSet<Week> weeks = new TreeSet<>();
		for (LocalDate day : dates) {
			Week w = new Week(day);
			weeks.add(w);
		}
		return weeks;
	}
	
	@Transactional(readOnly=true)
	public  Optional<ClientGain> findClientGainById(int id){ 
		return cgainRepo.findById(id);
	}
	
	@Transactional
	public List<ClientGain> findClientGainsForWeek(Week week, String dni) {
		Iterator<ClientGain> it = cgainRepo.findAll().iterator();
		List<ClientGain> result = new ArrayList<ClientGain>();
		while (it.hasNext()) {
			ClientGain cg = it.next();
			if (cg.getDni().equals(dni) && week.hasDay(cg.getDate()))
				result.add(cg);
		}
		return result;
	}

	@Transactional
	public  void save(ClientGain cgain) {
		cgainRepo.save(cgain);
	}

	public  void delete(ClientGain cgain) { 
		cgainRepo.delete(cgain);
	}
	
	public Collection<User> findUsers() throws DataAccessException{
		// TODO Auto-generated method stub
		return cgainRepo.findUsers();
	}
	
	public Collection<String> findClients() throws DataAccessException{
		// TODO Auto-generated method stub
		return cgainRepo.findClients();
	}
	
	public Collection<String> findGames() throws DataAccessException{
		// TODO Auto-generated method stub
		return cgainRepo.findGames();
	}
}
