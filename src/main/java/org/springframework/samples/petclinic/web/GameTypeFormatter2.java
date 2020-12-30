package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.stereotype.Component;

@Component
public class GameTypeFormatter2 implements Formatter<GameType> {

	private final CasinotableService casinotableService;
	   
    @Autowired
    public GameTypeFormatter2(CasinotableService casinotableService) {
        this.casinotableService = casinotableService;
    }

    @Override
    public String print(GameType gameType, Locale locale) {
        return gameType.getName();
    }

    @Override
    public GameType parse(String text, Locale locale) throws ParseException {
        Collection<GameType> findGameTypes = this.casinotableService.findGameTypes();
        for (GameType gameType : findGameTypes) {
            if (gameType.getName().equals(text)) {
                return gameType;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}
