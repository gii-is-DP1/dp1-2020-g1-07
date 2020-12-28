package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.repository.SlotGainRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SlotGainServiceTests {
	
	@Mock
	private SlotGainRepository slotGainRepo;
	
	protected SlotGainService slotGainService;
	
	@BeforeEach
    void setup() {
		slotGainService = new SlotGainService(slotGainRepo);
    }
	
	@Test
	void testAddingSlotGain() {
		SlotGain new_gain = new SlotGain();
		LocalDate date = LocalDate.of(2020, 3, 14);
		new_gain.setDate(date);
		new_gain.setAmount(100);
		
		Slotgame slotgame = new Slotgame();
		slotgame.setName("Game");
		slotgame.setJackpot(1000);
		
		Status status = new Status();
		status.setName("REPAIR");
		
		SlotMachine slotmachine = new SlotMachine();
		slotmachine.setSlotgame(slotgame);
		slotmachine.setStatus(status);
		Integer slotmachineid = slotmachine.getId();
		new_gain.setSlotMachine(slotmachine);
		
		Collection<SlotGain> sampleSlotGains = new ArrayList<SlotGain>();
		sampleSlotGains.add(new_gain);
        when(slotGainRepo.findAll()).thenReturn(sampleSlotGains);
		
        
		List<SlotGain> slotGains = StreamSupport.stream(this.slotGainService.findAll().spliterator(), false).collect(Collectors.toList());
		SlotGain saved_slotGain = slotGains.get(slotGains.size()-1);
		assertTrue(saved_slotGain.getDate().isEqual(date));
		assertTrue(saved_slotGain.getAmount()==100);
		assertTrue(saved_slotGain.getSlotMachine().getSlotgame().getName().equals("Game"));
		assertTrue(saved_slotGain.getSlotMachine().getStatus().getName().equals("REPAIR"));

	}
	
	/*
	//INSERT INTO slotgains VALUES (1,'1',100,'2010-09-07',1);
	@Test
	void shouldFindSlotGains() {
		List<SlotGain> slotgains = StreamSupport.stream(this.slotGainService.findAll().spliterator(), false).collect(Collectors.toList());
		
		SlotGain slotGain = EntityUtils.getById(slotgains, SlotGain.class, 2);
		assertThat(slotGain.getDate().equals(LocalDate.of(2010, 9, 07)));
		assertThat(slotGain.getSlotMachine().equals(1));
		assertThat(slotGain.getAmount().equals(100));
	}
	*/
}
