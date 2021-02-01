package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.stereotype.Component;

@Component
public class ArtistFormatter implements Formatter<Artist> {
	private final EventService eventService;
	   
    @Autowired
    public ArtistFormatter(EventService eventService) {
        this.eventService = eventService;
    }
    @Override
    public String print(Artist employee, Locale locale) {
        return employee.getDni();
    }
    @Override
    public Artist parse(String text, Locale locale) throws ParseException {
        Collection<Artist> findEmployees = this.eventService.findArtists();
        for (Artist employee : findEmployees) {
            if (employee.getDni().equals(text)) {
                return employee;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}
