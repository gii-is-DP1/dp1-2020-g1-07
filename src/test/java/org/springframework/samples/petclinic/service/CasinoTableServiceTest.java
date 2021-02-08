package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.repository.CasinotableRepository;

@ExtendWith(MockitoExtension.class)
public class CasinoTableServiceTest {
	
	@Mock
	private CasinotableRepository casinotableRepo;
	
	protected CasinotableService casinotableService;
	
	@BeforeEach
    void setup() {
		casinotableService = new CasinotableService(casinotableRepo);
    }
	
	@Test
	void testAddingCasTab() {
		Casinotable new_ct = new Casinotable();
		Game game = new Game();
		game.setName("Poker");
		GameType gametype = new GameType();
		gametype.setName("Cards");
		game.setGametype(gametype);
		game.setMaxPlayers(6);
		new_ct.setGame(game);
		new_ct.setGametype(gametype);
		Skill skill = new Skill();
		skill.setName("Amateur");
		new_ct.setSkill(skill);
		new_ct.setDate(LocalDate.of(2020, 1, 30));
		new_ct.setEndingTime("20:00:00");
		new_ct.setStartTime("19:00:00");
		Collection<Casinotable> sampleCasinotables = new ArrayList<Casinotable>();
		sampleCasinotables.add(new_ct);
        when(casinotableRepo.findAll()).thenReturn(sampleCasinotables);
		
        
		List<Casinotable> casinotables = StreamSupport.stream(this.casinotableService.findAll().spliterator(), 
				false).collect(Collectors.toList());
		
		Casinotable saved_ct = casinotables.get(0);
		assertTrue(saved_ct.getGame().getName().equals("Poker"));
		assertTrue(saved_ct.getGame().getGametype().getName().equals("Cards") 
		&& saved_ct.getGametype().getName().equals(saved_ct.getGame().getGametype().getName()));
		assertTrue(saved_ct.getSkill().getName().equals("Amateur"));
		assertTrue(saved_ct.getGame().getMaxPlayers() == 6);
		assertTrue(saved_ct.getEndingTime().equals("20:00:00") && saved_ct.getStartTime().equals("19:00:00"));
		
	}
}

