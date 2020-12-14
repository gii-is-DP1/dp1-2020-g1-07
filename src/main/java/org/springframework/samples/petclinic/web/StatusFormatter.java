package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.service.SlotMachineService;
import org.springframework.stereotype.Component;

@Component
public class StatusFormatter implements Formatter<Status>{

	private final SlotMachineService slotMachineService;

	@Autowired
	public StatusFormatter(SlotMachineService slotMachineService) {
		this.slotMachineService = slotMachineService;
	}

	@Override
	public String print(Status status, Locale locale) {
		return status.getName();
	}

	@Override
	public Status parse(String text, Locale locale) throws ParseException {
		Collection<Status> findStatuses = this.slotMachineService.findStatus();
		for (Status status : findStatuses) {
			if (status.getName().equals(text)) {
				return status;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}
	
}
