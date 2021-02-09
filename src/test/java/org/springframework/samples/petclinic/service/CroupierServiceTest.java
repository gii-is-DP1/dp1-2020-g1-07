package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Iterator;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Croupier;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.repository.CroupierRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CroupierServiceTest {

	@Mock
	private CroupierRepository croupierRepo;
	
	@Autowired
	protected CroupierService croupierService;
	
	@BeforeEach
    void setup() {
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
		new_ct.setName("Mesa 1");
		
		Croupier crp1 = new Croupier();
		crp1.setId(1);
		crp1.setDni("11111111A");
		crp1.setName("William Carter");
		crp1.setPhone_number("111222333");
		crp1.setCasinotable(new_ct);
		
		Croupier crp2 = new Croupier();
		crp2.setId(2);
		crp2.setDni("22222222B");
		crp2.setName("John Titor");
		crp2.setPhone_number("444555666");
		crp2.setCasinotable(new_ct);
		
		Croupier crp3 = new Croupier();
		crp3.setId(3);
		crp3.setDni("33333333C");
		crp3.setName("Gordon Freeman");
		crp3.setPhone_number("777888999");
		crp3.setCasinotable(new_ct);
		given(this.croupierRepo.findAll()).willReturn(Lists.list(crp1, crp2, crp3));
		croupierService = new CroupierService(croupierRepo);
    }
	
	@Test
	void findCroupierTest() {
		Iterator<Croupier> it = this.croupierService.findAll().iterator();
		Croupier crp1 = it.next();
		assertThat(crp1.getDni().equals("11111111A"));
		assertThat(crp1.getName().equals("William Carter"));
		assertThat(crp1.getPhone_number().equals("444555666"));
		assertThat(crp1.getCasinotable().getName().equals("Mesa 1"));
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}
