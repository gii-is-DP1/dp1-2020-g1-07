package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
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
		event.setArtist_id(artist);
		Collection<Event> sampleEvents = new ArrayList<Event>();
		sampleEvents.add(event);
        when(eventRepo.findAll()).thenReturn(sampleEvents);
		
		List<Event> events = StreamSupport.stream(this.eventService.findAll().spliterator(), false).collect(Collectors.toList());
		Event saved_event = events.get(0);
		assertTrue(saved_event.getName().equals("Hamlet"));
		assertTrue(saved_event.getArtist_id().getName().equals("Luis Martín"));
		assertTrue(saved_event.getShowtype_id().getName().equals("Theater"));
		
	}
}
