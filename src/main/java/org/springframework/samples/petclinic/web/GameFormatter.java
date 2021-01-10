package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.stereotype.Component;

@Component
public class GameFormatter implements Formatter<Game>{
	private final CasinotableService casinotableService;
	   
    @Autowired
    public GameFormatter(CasinotableService casinotableService) {
        this.casinotableService = casinotableService;
    }
    @Override
    public String print(Game game, Locale locale) {
        return game.getName();
    }
    @Override
    public Game parse(String text, Locale locale) throws ParseException {
        Collection<Game> findGames = this.casinotableService.findGames();
        for (Game game : findGames) {
            if (game.getName().equals(text)) {
                return game;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}
