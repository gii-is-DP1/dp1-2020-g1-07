package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.MaintenanceWorker;
import org.springframework.samples.petclinic.repository.MaintenanceWorkerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaintenanceWorkerService {
	@Autowired
	private  MaintenanceWorkerRepository maintenanceWorkerRep; 
	
	@Transactional
	public int maintenanceWorkerCount() {
		return (int)maintenanceWorkerRep.count();
	}
	
	@Transactional
	public Iterable<MaintenanceWorker> findAll() {
		return maintenanceWorkerRep.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<MaintenanceWorker> findMaintenanceWorkerById(int id){ 
		return maintenanceWorkerRep.findById(id);
	}

	@Transactional
	public  void save(MaintenanceWorker maintenanceWorker) {   
		maintenanceWorkerRep.save(maintenanceWorker);
	}

	public  void delete(MaintenanceWorker maintenanceWorker) { 
		maintenanceWorkerRep.delete(maintenanceWorker);
	}
}
