package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.repository.CasinotableRepository;
import org.springframework.samples.petclinic.repository.DishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DishService {
	@Autowired
	private  DishRepository dishRepo; ///Cambiado a static aunque no viene en el video
	
	@Transactional
	public int casinoTableCount() {
		return (int)dishRepo.count();
	}
	
	@Transactional
	public Iterable<Dish> findAll() {
		return dishRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Dish> findCasinotableById(int id){ ///Cambiado a static aunque no viene en el video
		return dishRepo.findById(id);
	}

	@Transactional
	public  void save(Dish dish) {   ///Cambiado a static aunque no viene en el video
		dishRepo.save(dish);
	}

	public  void delete(Dish dish) { ///Cambiado a static aunque no viene en el video
		dishRepo.delete(dish);
	}
}
