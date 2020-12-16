package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Event;

import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EventServiceTest {

	@Autowired
	private EventService eventService;
	
	@Test
	void shouldFindEvent() {
		List<Event> events = StreamSupport.stream(this.eventService.findAll().spliterator(), false).collect(Collectors.toList());

		Event event = EntityUtils.getById(events, Event.class, 1);
		assertThat(event.getId()).isEqualTo(1);
		assertThat(event.getName()).isEqualTo("Magic and Pasion");
		assertThat(event.getDate()).isEqualTo("2019-12-21");
		assertThat(event.getShowtype_id()).isEqualTo(3);
		assertThat(event.getArtist_id()).isEqualTo(4);
	}
	
}
