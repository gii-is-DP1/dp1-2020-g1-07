package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClientServiceTests {
	/*
	@Mock
	private ClientRepository clientRepo;
	
	@Autowired
	protected ClientService clientService;
	

	@Test
	public void testCountWithInitialData() {
		int count= clientService.clientCount();
		assertEquals(count,3);
	}*/
	/*
	@Test
	void testAddingClient() {
		Client new_client = new Client();
		new_client.setDni("45324586J");
		new_client.setName("Paco Perez");
		new_client.setPhone_number("643213480");
		
		Collection<Client> sampleClients = new ArrayList<Client>();
		sampleClients.add(new_client);
        when(clientRepo.findAll()).thenReturn(sampleClients);
		
        
		List<Client> clients = StreamSupport.stream(this.clientService.findAll().spliterator(), false).collect(Collectors.toList());
		Client saved_client = clients.get(clients.size()-1);
		assertTrue(saved_client.getDni().equals("45324586J"));
		assertTrue(saved_client.getName().equals("Paco Perez"));
		assertTrue(saved_client.getPhone_number().equals("643213480"));
	}*/
}
