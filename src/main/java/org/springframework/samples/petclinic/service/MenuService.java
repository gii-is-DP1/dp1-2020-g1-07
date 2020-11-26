package org.springframework.samples.petclinic.service;

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
	private  MenuRepository menuRepo; ///Cambiado a static aunque no viene en el video
	
	@Transactional
	public int menuCount() {
		return (int)menuRepo.count();
	}
	
	@Transactional
	public Iterable<Menu> findAll() {
		return menuRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Menu> findMenuById(int id){ ///Cambiado a static aunque no viene en el video
		return menuRepo.findById(id);
	}

	@Transactional
	public  void save(Menu menu) {   ///Cambiado a static aunque no viene en el video
		menuRepo.save(menu);
	}

	public  void delete(Menu menu) { ///Cambiado a static aunque no viene en el video
		menuRepo.delete(menu);
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
}
