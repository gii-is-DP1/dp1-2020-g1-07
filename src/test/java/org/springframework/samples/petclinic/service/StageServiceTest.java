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
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.repository.StageRepository;

@ExtendWith(MockitoExtension.class)
public class StageServiceTest {

	@Mock
	private StageRepository stageRepo;
	
	protected StageService stageService;
	
	@BeforeEach
    void setup() {
		stageService = new StageService(stageRepo);
    }
	@Test
	public void testCountWithInitialData() {
		int count= stageService.stageCount();
		assertEquals(count,0);
	}
	@Test
	void testAddingStage() {
		Stage new_stage = new Stage();
		new_stage.setCapacity(16);
		Event event = new Event();
		event.setName("Hamlet");
		event.setDate(LocalDate.parse("2020-02-17"));
		new_stage.setEvent_id(event);
		ShowType showtype = new ShowType();
		showtype.setName("Theater");
		event.setShowtype_id(showtype);
		Artist artist = new Artist();
		artist.setName("Luis Martín");
		artist.setDni("23213422B");
		artist.setPhone_number("65241563");
		event.setArtist_id(artist);
		Collection<Stage> sampleStages = new ArrayList<Stage>();
		sampleStages.add(new_stage);
        when(stageRepo.findAll()).thenReturn(sampleStages);
		
        
		List<Stage> stages = StreamSupport.stream(this.stageService.findAll().spliterator(), false).collect(Collectors.toList());
		Stage saved_staged = stages.get(0);
		assertTrue(saved_staged.getCapacity() == 16);
		assertTrue(saved_staged.getEvent_id().getName().equals("Hamlet"));
		assertTrue(saved_staged.getEvent_id().getArtist_id().getName().equals("Luis Martín"));
		assertTrue(saved_staged.getEvent_id().getShowtype_id().getName().equals("Theater"));
		
	}

}