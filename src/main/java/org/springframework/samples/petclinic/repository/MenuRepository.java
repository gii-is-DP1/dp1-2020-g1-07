package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;

public interface MenuRepository extends CrudRepository<Menu, Integer>{
	@Query("SELECT dish FROM Dish dish ORDER BY dish.id")
	List<Dish> findDishes() throws DataAccessException;
	
	@Query("SELECT shift FROM Shift shift ORDER BY shift.id")
	List<Shift> findShifts() throws DataAccessException;

}
