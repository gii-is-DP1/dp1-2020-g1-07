package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Menu;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.model.Slotgame;
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
	
	@Test
	void testAddingNewSlotgame() {
		Slotgame slotgame = new Slotgame();
		slotgame.setName("New game");
		slotgame.setJackpot(75);
		Collection<Slotgame> sampleSlotgames = new ArrayList<Slotgame>();
		sampleSlotgames.add(slotgame);
        when(slotgameRepo.findAll()).thenReturn(sampleSlotgames);
		
		List<Slotgame> slotgames = StreamSupport.stream(this.slotgameService.findAll().spliterator(), false).collect(Collectors.toList());
		Slotgame saved_slotgame = slotgames.get(slotgames.size()-1);
		assertThat(saved_slotgame.getName().equals("New game"));
		assertThat(saved_slotgame.getJackpot()==75);
	}
	
}
