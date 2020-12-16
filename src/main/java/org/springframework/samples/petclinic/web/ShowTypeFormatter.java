package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import org.springframework.samples.petclinic.model.ShowType;
import org.springframework.samples.petclinic.service.EventService;

import org.springframework.stereotype.Component;

@Component
public class ShowTypeFormatter implements Formatter<ShowType> {
	
	 	private final EventService eventservice;
	   
	    @Autowired
	    public ShowTypeFormatter(EventService eventservice) {
	        this.eventservice = eventservice;
	    }

	    @Override
	    public String print(ShowType showType, Locale locale) {
	        return showType.getName();
	    }

	    @Override
	    public ShowType parse(String text, Locale locale) throws ParseException {
	        Collection<ShowType> findShowTypes = this.eventservice.findShowTypes();
	        for (ShowType showType : findShowTypes) {
	            if (showType.getName().equals(text)) {
	                return showType;
	            }
	        }
	        throw new ParseException("type not found: " + text, 0);
	   }

}
