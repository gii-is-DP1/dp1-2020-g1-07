package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.stereotype.Repository;

@Repository("menuRepository")
public interface MenuRepository extends CrudRepository<Menu, Integer>{
	
	@Query("SELECT dish FROM Dish dish WHERE dish.dish_course.id=1 ORDER BY dish.id")
	List<Dish> findFirstDishes() throws DataAccessException;
	
	@Query("SELECT dish FROM Dish dish WHERE dish.dish_course.id=2 ORDER BY dish.id")
	List<Dish> findSecondDishes() throws DataAccessException;
	
	@Query("SELECT dish FROM Dish dish WHERE dish.dish_course.id=3 ORDER BY dish.id")
	List<Dish> findDesserts() throws DataAccessException;
	
	@Query("SELECT shift FROM Shift shift WHERE shift.id!=4 ORDER BY shift.id")
	List<Shift> findShifts() throws DataAccessException;
	
	@Query("SELECT dish FROM Dish dish WHERE dish.shift.id = :id AND dish.dish_course.id=1 ORDER BY dish.id")
	public List<Dish> findFirstDishesByShift(@Param("id") int id);
	
	@Query("SELECT dish FROM Dish dish WHERE dish.shift.id = :id AND dish.dish_course.id=2 ORDER BY dish.id")
	public List<Dish> findSecondDishesByShift(@Param("id") int id);

	@Query("SELECT dish FROM Dish dish WHERE dish.shift.id = :id AND dish.dish_course.id=3 ORDER BY dish.id")
	public List<Dish> findDessertsByShift(@Param("id") int id);

	@Query("SELECT DISTINCT date FROM Menu")
	public List<LocalDate> findAllDates();

	@Query("SELECT menu FROM Menu menu where menu.date = :date ORDER BY menu.shift.id")
	public List<Menu> findMenusByDate(@Param("date") LocalDate date);


}
