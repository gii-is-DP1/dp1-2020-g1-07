package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Slotgame;
import org.springframework.samples.petclinic.service.SlotMachineService;
import org.springframework.stereotype.Component;

@Component
public class SlotgameFormatter implements Formatter<Slotgame> {
	
	private final SlotMachineService slotMachineService;
	   
    @Autowired
    public SlotgameFormatter(SlotMachineService slotMachineService) {
        this.slotMachineService = slotMachineService;
    }
    @Override
    public String print(Slotgame slotgame, Locale locale) {
        return slotgame.getName();
    }
    @Override
    public Slotgame parse(String text, Locale locale) throws ParseException {
        Collection<Slotgame> findSlotgames = this.slotMachineService.findSlotgames();
        for (Slotgame slotgame : findSlotgames) {
            if (slotgame.getName().equals(text)) {
                return slotgame;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }

}
