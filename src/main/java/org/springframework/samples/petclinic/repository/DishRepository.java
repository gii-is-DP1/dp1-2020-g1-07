package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;

public interface DishRepository extends CrudRepository<Dish, Integer>{

	@Query("SELECT dcourse FROM DishCourse dcourse ORDER BY dcourse.id")
	List<DishCourse> findDishCourses() throws DataAccessException;
	
	@Query("SELECT shift FROM Shift shift WHERE shift.id!=4 ORDER BY shift.id")
	List<Shift> findShifts() throws DataAccessException;

}
