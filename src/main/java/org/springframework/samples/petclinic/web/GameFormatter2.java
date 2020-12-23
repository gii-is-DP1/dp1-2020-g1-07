package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.service.ClientGainService;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Game;

import org.springframework.stereotype.Component;

@Component
public class GameFormatter2 implements Formatter<Game>{
	private final ClientGainService cgainService;
	   
    @Autowired
    public GameFormatter2(ClientGainService cgainService) {
        this.cgainService = cgainService;
    }
    @Override
    public String print(Game game, Locale locale) {
        return game.getName();
    }
    @Override
    public Game parse(String text, Locale locale) throws ParseException {
        Collection<Game> findGames = this.cgainService.findGames();
        for (Game game : findGames) {
            if (game.getName().equals(text)) {
                return game;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}