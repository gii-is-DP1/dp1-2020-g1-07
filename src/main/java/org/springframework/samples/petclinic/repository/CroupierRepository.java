package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.stereotype.Repository;

@Repository("croupierRepository")
public interface CroupierRepository extends CrudRepository<Croupier, Integer>{

	@Query("SELECT casinotable FROM Casinotable casinotable ORDER BY casinotable.id")
	public List<Casinotable> findCasinoTables();
	
	
	
}
