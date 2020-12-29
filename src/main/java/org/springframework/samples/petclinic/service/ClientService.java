package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
	
	@Autowired
	private  ClientRepository clientRepo;
	
	public ClientService(ClientRepository clientRepo) {
		this.clientRepo = clientRepo;
	}

	@Transactional
	public int clientCount() {
		return (int)clientRepo.count();
	}
	
	@Transactional
	public Iterable<Client> findAll() {
		return clientRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Client> findClientById(int id){ ///Cambiado a static aunque no viene en el video
		return clientRepo.findById(id);
	}

	@Transactional
	public  void save(Client client) {   ///Cambiado a static aunque no viene en el video
		clientRepo.save(client);
	}

	public  void delete(Client client) { ///Cambiado a static aunque no viene en el video
		clientRepo.delete(client);
	}

}
