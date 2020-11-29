package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
	@Autowired
	private  MenuRepository menuRepo;
	
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
		return menuRepo.findFirstDishesByShift(id);
	}
	
	public Collection<Dish> findSecondDishesByShift(int id) throws DataAccessException{
		// TODO Auto-generated method stub
		return menuRepo.findSecondDishesByShift(id);
	}
	
	public Collection<Dish> findDessertsByShift(int id) throws DataAccessException{
		// TODO Auto-generated method stub
		return menuRepo.findDessertsByShift(id);
	}
	
	public Collection<Dish> findFirstDishes() throws DataAccessException{
		// TODO Auto-generated method stub
		return menuRepo.findFirstDishes();
	}
	
	public Collection<Dish> findSecondDishes() throws DataAccessException{
		// TODO Auto-generated method stub
		return menuRepo.findSecondDishes();
	}
	
	public Collection<Dish> findDesserts() throws DataAccessException{
		// TODO Auto-generated method stub
		return menuRepo.findDesserts();
	}
	
	public Collection<Shift> findShifts() throws DataAccessException{
		// TODO Auto-generated method stub
		return menuRepo.findShifts();
	}

	public Collection<Menu> findMenusByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return menuRepo.findMenusByDate(date);
	}
}
