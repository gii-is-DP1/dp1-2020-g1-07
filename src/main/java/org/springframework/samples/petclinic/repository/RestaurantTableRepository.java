package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends CrudRepository<RestaurantTable, Integer>{
	@Query("SELECT restaurantTable FROM RestaurantTable restaurantTable ORDER BY restaurantTable.id")
	List<RestaurantTable> findRestaurantTables() throws DataAccessException;
	
	@Query("SELECT waiter FROM Waiter waiter ORDER BY waiter.id")
	List<Waiter> findWaiters() throws DataAccessException;
	
}
