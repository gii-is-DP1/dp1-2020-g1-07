package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.stereotype.Repository;

@Repository("waiterRepository")
public interface WaiterRepository extends CrudRepository<Waiter, Integer>{

	@Query("SELECT waiter.serves FROM Waiter waiter WHERE waiter.id=:waiterId")
	public List<RestaurantTable> findTablesServed(@Param("waiterId")int waiterId) throws DataAccessException;
	
	@Query("SELECT waiter.serves FROM Waiter waiter WHERE NOT waiter.id=:waiterId")
	public List<RestaurantTable> findTablesNotServedIds(@Param("waiterId")int waiterId) throws DataAccessException;

}
