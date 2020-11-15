package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.MaintenanceWorker;

public interface MaintenanceWorkerRepository extends CrudRepository<MaintenanceWorker, String>{

}
