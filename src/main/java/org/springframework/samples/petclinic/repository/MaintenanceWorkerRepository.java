package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.MaintenanceWorker;
import org.springframework.stereotype.Repository;

@Repository("maintenanceWorkerRepository")
public interface MaintenanceWorkerRepository extends CrudRepository<MaintenanceWorker, Integer>{

}
