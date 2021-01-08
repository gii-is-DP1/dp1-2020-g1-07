package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.repository.SlotMachineRepository;

@ExtendWith(MockitoExtension.class)
public class SlotMachineServiceTests {
	
	@Mock
	private SlotMachineRepository slotMachineRepo;
	
	protected SlotMachineService slotMachineService;

	@BeforeEach
    void setup() {
		slotMachineService = new SlotMachineService(slotMachineRepo);
    }
	
	@Test
	void testAddingNewSlotMachine() {
		SlotMachine slotMachine = new SlotMachine();
		Status status = new Status();
		status.setName("COLLECT");
		slotMachine.setStatus(status);
		Slotgame slotgame = new Slotgame();
		slotgame.setName("Game 1");
		slotgame.setJackpot(100);
		slotMachine.setSlotgame(slotgame);
		Collection<SlotMachine> sampleSlotMachines = new ArrayList<SlotMachine>();
		sampleSlotMachines.add(slotMachine);
        when(slotMachineRepo.findAll()).thenReturn(sampleSlotMachines);
		
		List<SlotMachine> slotMachines = StreamSupport.stream(this.slotMachineService.findAll().spliterator(), false).collect(Collectors.toList());
		SlotMachine saved_slotMachine = slotMachines.get(slotMachines.size()-1);
		assertTrue(saved_slotMachine.getStatus().getName().equals("COLLECT"));		
		assertTrue(saved_slotMachine.getSlotgame().getName().equals("Game 1"));
	}
}
