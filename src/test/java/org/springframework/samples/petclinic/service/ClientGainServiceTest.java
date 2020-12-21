package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.repository.ClientGainRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class ClientGainServiceTest {

	@Mock
	private ClientGainRepository cgainRepo;
	
	protected ClientGainService cgainService;
	
	@BeforeAll
    void setup() {
		cgainService = new ClientGainService(cgainRepo);
    }
    
}
