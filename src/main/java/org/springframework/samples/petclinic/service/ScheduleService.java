package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ScheduleService {
	
	@Autowired
	private  ScheduleRepository scheduleRep; 
	
	@Transactional
	public int scheduleCount() {
		return (int)scheduleRep.count();
	}
	
	@Transactional
	public Iterable<Schedule> findAll() {
		return scheduleRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Schedule> findScheduleById(int id){ 
		return scheduleRep.findById(id);
	}

	@Transactional
	public  void save(Schedule schedule) {   
		scheduleRep.save(schedule);
	}

	public  void delete(Schedule schedule) { 
		scheduleRep.delete(schedule);
	}
	
	public Collection<Shift> findShifts() throws DataAccessException{
        // TODO Auto-generated method stub
		log.info("Loading shifts from DB");
        return scheduleRep.findShifts();
    }
	public Collection<Employee> findEmployees() throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading employees from DB");
		return scheduleRep.findEmployees();
	}
	
	public Employee findEmployeeByUsername(String username) throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading a employee for a username: "+ username);
		return scheduleRep.findEmployeeByUsername(username);
	}
	
	public  Collection<String> findScheduleByDni() throws DataAccessException{ 
		log.info("Loading schedule for a dni from DB");
		return scheduleRep.findEmployeeDni();
	}


}
