package org.springframework.samples.petclinic.web;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.service.CroupierService;
import org.springframework.stereotype.Component;

@Component
public class CasinotableFormatter implements Formatter<Casinotable>{
	private final CroupierService croupierService;
	   
    @Autowired
    public CasinotableFormatter(CroupierService croupierService) {
        this.croupierService = croupierService;
    }
    @Override
    public String print(Casinotable casinotable, Locale locale) {
        return casinotable.getName();
    }
    @Override
    public Casinotable parse(String text, Locale locale) throws ParseException {
        Collection<Casinotable> findCasinotables = this.croupierService.findCasinotables();
        for (Casinotable casinotable : findCasinotables) {
            if (casinotable.getName().equals(text)) {
                return casinotable;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}
