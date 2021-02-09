package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.service.EventService;


public class StageFormatter implements Formatter<Stage>{
	private final EventService eventService;
	   
    @Autowired
    public StageFormatter(EventService eventService) {
        this.eventService = eventService;
    }
    @Override
    public String print(Stage stage, Locale locale) {
        return stage.getId().toString();
    }
    @Override
    public Stage parse(String text, Locale locale) throws ParseException {
        Collection<Stage> findStages = this.eventService.findStages();
        for (Stage stage : findStages) {
            if (stage.getId().toString().equals(text)) {
                return stage;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}
