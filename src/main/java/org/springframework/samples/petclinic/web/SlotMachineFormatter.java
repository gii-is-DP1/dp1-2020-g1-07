package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.service.SlotGainService;
import org.springframework.stereotype.Component;

@Component
public class SlotMachineFormatter implements Formatter<SlotMachine>{

	private final SlotGainService slotGainService;

	@Autowired
	public SlotMachineFormatter(SlotGainService slotGainService) {
		this.slotGainService = slotGainService;
	}

	@Override
	public String print(SlotMachine slotMachine, Locale locale) {
		return slotMachine.getId().toString();
	}

	@Override
	public SlotMachine parse(String text, Locale locale) throws ParseException {
		Collection<SlotMachine> findSlotMachines = this.slotGainService.findSlotMachines();
		for (SlotMachine slotMachine : findSlotMachines) {
			if (slotMachine.getId().toString().equals(text)) {
				return slotMachine;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}
	
}
