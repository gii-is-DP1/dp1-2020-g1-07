package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.service.ShowReservationService;
import org.springframework.stereotype.Component;

@Component
public class EventFormatter2 implements Formatter<Event>{

	private final ShowReservationService showresService;
	   
    @Autowired
    public EventFormatter2(ShowReservationService showresService) {
        this.showresService = showresService;
    }
    @Override
    public String print(Event event, Locale locale) {
        return event.getName();
    }
    
    @Override
    public Event parse(String text, Locale locale) throws ParseException {
        Collection<Event> findEvents = this.showresService.findAvailableShows();
        for (Event event : findEvents) {
            if (event.getName().equals(text)) {
                return event;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
	
	
}
