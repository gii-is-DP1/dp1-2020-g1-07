package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.DishCourse;
import org.springframework.samples.petclinic.model.Shift;

public interface DishRepository extends CrudRepository<Dish, Integer>{

	@Query("SELECT dcourse FROM DishCourse dcourse ORDER BY dcourse.id")
	List<DishCourse> findDishCourses() throws DataAccessException;
	
	@Query("SELECT shift FROM Shift shift WHERE shift.id!=4 ORDER BY shift.id")
	List<Shift> findShifts() throws DataAccessException;

	@Query("SELECT dish FROM Dish dish WHERE dish.name LIKE :name ORDER BY dish.name")
	Optional<Dish> findDishByName(@Param("name")String name);

}
