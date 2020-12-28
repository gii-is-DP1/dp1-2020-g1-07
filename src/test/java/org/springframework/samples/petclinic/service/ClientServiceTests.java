package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.samples.petclinic.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClientServiceTests {
	@Autowired
	protected ClientService clientService;

	@Test
	void shouldFindMenus() {
		List<Client> clients = StreamSupport.stream(this.clientService.findAll().spliterator(), false).collect(Collectors.toList());
		Client client = EntityUtils.getById(clients, Client.class, 1);
		assertThat(client.getName()).isEqualTo("Ofelia Bustos");
		assertThat(client.getDni()).isEqualTo("11111111A");
		assertThat(client.getPhone_number()).isEqualTo("444444444");
	}
}
