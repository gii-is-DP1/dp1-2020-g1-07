package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cook;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.stereotype.Repository;

@Repository("cookRepository")
public interface CookRepository extends CrudRepository<Cook, Integer>{
	
	@Query("SELECT cook.prepares FROM Cook cook WHERE cook.id=:cookId")
	public List<Dish> findPreparedDishes(@Param("cookId")int cookId) throws DataAccessException;
	
	@Query("SELECT dish FROM Dish dish ORDER BY dish.id")
	public List<Dish> findDishes() throws DataAccessException;

}
