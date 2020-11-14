package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;

public interface MenuRepository extends CrudRepository<Menu, Integer>{
	
	@Query("SELECT dish FROM Dish dish WHERE dish.id=1 ORDER BY dish.id")
	List<Dish> findFirstDishes() throws DataAccessException;
	
	@Query("SELECT dish FROM Dish dish WHERE dish.id=2 ORDER BY dish.id")
	List<Dish> findSecondDishes() throws DataAccessException;
	
	@Query("SELECT dish FROM Dish dish WHERE dish.id=3 ORDER BY dish.id")
	List<Dish> findDesserts() throws DataAccessException;
	
	@Query("SELECT shift FROM Shift shift WHERE shift.id!=4 ORDER BY shift.id")
	List<Shift> findShifts() throws DataAccessException;

}
