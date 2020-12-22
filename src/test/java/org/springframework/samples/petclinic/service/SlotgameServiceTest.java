package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.repository.SlotgameRepository;

@ExtendWith(MockitoExtension.class)
public class SlotgameServiceTest {

	@Mock
	private SlotgameRepository slotgameRepo;
	
	protected SlotgameService slotgameService;

	@BeforeEach
    void setup() {
		slotgameService = new SlotgameService(slotgameRepo);
    }
}
