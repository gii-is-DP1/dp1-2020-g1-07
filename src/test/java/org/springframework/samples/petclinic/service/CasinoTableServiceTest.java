package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
	public void testCountWithInitialData() {
		int count= casinotableService.casinoTableCount();
		assertEquals(count,0);
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
		new_ct.setName("Mesa 1");
		Skill skill = new Skill();
		skill.setName("Amateur");
		new_ct.setSkill(skill);
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
		assertTrue(saved_ct.getName().equals("Mesa 1"));
		
	}
}

