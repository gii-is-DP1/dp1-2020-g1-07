package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.springframework.samples.petclinic.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

	@Mock
	private EventRepository eventRepo;
	
	protected EventService eventService;
	
	@BeforeEach
    void setup() {
		eventService = new EventService(eventRepo);
    }
	@Test
	public void testCountWithInitialData() {
		int count= eventService.eventCount();
		assertEquals(count,0);
	}
	@Test
	void testAddingEvent() {
		
		Stage stage = new Stage();
		stage.setId(1);
		stage.setCapacity(100);
		Event event = new Event();
		event.setName("Hamlet");
		event.setDate(LocalDate.parse("2020-02-17"));
		ShowType showtype = new ShowType();
		showtype.setName("Theater");
		event.setShowtype_id(showtype);
		Artist artist = new Artist();
		artist.setName("Luis Martín");
		artist.setDni("23213422B");
		artist.setPhone_number("65241563");
		Set<Artist> artists = new HashSet<Artist>();
		artists.add(artist);
		event.setArtists(artists);
		event.setStage_id(stage);
		Collection<Event> sampleEvents = new ArrayList<Event>();
		sampleEvents.add(event);
        when(eventRepo.findAll()).thenReturn(sampleEvents);
		
		List<Event> events = StreamSupport.stream(this.eventService.findAll().spliterator(), false).collect(Collectors.toList());
		Event saved_event = events.get(0);
		assertTrue(saved_event.getName().equals("Hamlet"));
		assertTrue(saved_event.getArtists().stream().findFirst().get().getName().equals("Luis Martín"));
		assertTrue(saved_event.getShowtype_id().getName().equals("Theater"));
		assertTrue(saved_event.getStage_id().getCapacity() == 100);
		
	}
}
