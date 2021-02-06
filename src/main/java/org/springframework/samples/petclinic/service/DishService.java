package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.repository.DishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DishService {
	
	@Autowired
	private  DishRepository dishRepo; ///Cambiado a static aunque no viene en el video
	
	@Autowired
	public DishService(DishRepository dishRepo) {
		this.dishRepo = dishRepo;
	}		
	
	@Transactional
	public int dishCount() {
		return (int)dishRepo.count();
	}
	
	@Transactional
	public Iterable<Dish> findAll() {
		return dishRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Dish> findDishById(int id){ ///Cambiado a static aunque no viene en el video
		return dishRepo.findById(id);
	}

	@Transactional
	public  void save(Dish dish) {   ///Cambiado a static aunque no viene en el video
		dishRepo.save(dish);
	}

	public  void delete(Dish dish) { ///Cambiado a static aunque no viene en el video
		dishRepo.delete(dish);
	}

	public Collection<DishCourse> findDishCourses() throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading dish courses from DB");
		return dishRepo.findDishCourses();
	}
	
	public Collection<Shift> findShifts() throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading shifts from DB");
		return dishRepo.findShifts();
	}

	public Optional<Dish> findDishByName(String name) {
		// TODO Auto-generated method stub
		return dishRepo.findDishByName(name);
	}
}
