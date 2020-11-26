package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.service.GameService;
import org.springframework.stereotype.Component;

@Component
public class GameTypeFormatter implements Formatter<GameType>  {
    
    private final GameService gameService;
   
    @Autowired
    public GameTypeFormatter(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String print(GameType gameType, Locale locale) {
        return gameType.getName();
    }

    @Override
    public GameType parse(String text, Locale locale) throws ParseException {
        Collection<GameType> findGameTypes = this.gameService.findGameTypes();
        for (GameType gameType : findGameTypes) {
            if (gameType.getName().equals(text)) {
                return gameType;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }

}