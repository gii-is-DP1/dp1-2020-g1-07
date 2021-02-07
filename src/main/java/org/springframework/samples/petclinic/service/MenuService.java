package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class MenuService {
	
	private  MenuRepository menuRepo;
	
	@Autowired
	public MenuService(MenuRepository menuRepo) {
		this.menuRepo = menuRepo;
	}
	
	@Transactional
	public int menuCount() {
		return (int)menuRepo.count();
	}
	
	@Transactional
	public Iterable<Menu> findAll() {
		return menuRepo.findAll();
	}
	
	@Transactional
	public Collection<LocalDate> findAllDates() {
		return menuRepo.findAllDates();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Menu> findMenuById(int id){ 
		return menuRepo.findById(id);
	}

	@Transactional
	public  void save(Menu menu) {   
		menuRepo.save(menu);
	}

	public  void delete(Menu menu) { 
		menuRepo.delete(menu);
	}
	
	public Collection<Dish> findFirstDishesByShift(int id) throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading the first dishes by the shift with id: " + id);
		return menuRepo.findFirstDishesByShift(id);
	}
	
	public Collection<Dish> findSecondDishesByShift(int id) throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading the second dishes by the shift with id: " + id);
		return menuRepo.findSecondDishesByShift(id);
	}
	
	public Collection<Dish> findDessertsByShift(int id) throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading the desserts by the shift with id: " + id);
		return menuRepo.findDessertsByShift(id);
	}
	
	public Collection<Dish> findFirstDishes() throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading first dishes from DB");
		return menuRepo.findFirstDishes();
	}
	
	public Collection<Dish> findSecondDishes() throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading second dishes from DB");
		return menuRepo.findSecondDishes();
	}
	
	public Collection<Dish> findDesserts() throws DataAccessException{
		// TODO Auto-generated method stub
		log.info("Loading desserts from DB");
		return menuRepo.findDesserts();
	}
	
	public Collection<Shift> findShifts() throws DataAccessException{
		// TODO Auto-generated method stub´
		log.info("Loading shifts from DB");
		return menuRepo.findShifts();
	}

	public Collection<Menu> findMenusByDate(LocalDate date) {
		// TODO Auto-generated method stub
		log.info("Loading menus by date: " + date);
		return menuRepo.findMenusByDate(date);
	}

	public List<String> findSchedulesDnisByShiftAndDate(Shift shift, LocalDate date) {
		// TODO Auto-generated method stub
		log.info("Loading Dnis by shift: " + shift + " and date: " + date);
		return menuRepo.findSchedulesDnisByShiftAndDate(shift,date);
	}

	public List<Cook> findCooks() {
		// TODO Auto-generated method stub
		log.info("Loading cooks from DB");
		return menuRepo.findCooks();
	}
}
