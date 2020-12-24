package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dish;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.service.SlotGainService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SlotGainValidator implements Validator{
	
	private static final String REQUIRED = "required";
	
	@Autowired
	private SlotGainService slotGainService;
	
	public SlotGain getSlotGainwithIdDifferent(SlotMachine SM, LocalDate date) {
		SlotGain empty_slotGain = new SlotGain();
		List<SlotGain> slotGains = StreamSupport.stream(this.slotGainService.findAll().spliterator(), false).collect(Collectors.toList());
		for (SlotGain slotGain : slotGains) {
			if (slotGain.getSlotMachine().getId().equals(SM.getId()) && slotGain.getDate().isEqual(date)) {
				return slotGain;
			}
		}
		return empty_slotGain;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SlotGain.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SlotGain slotGain = (SlotGain) target;
		LocalDate date = slotGain.getDate();
		Integer amount = slotGain.getAmount();
		SlotMachine slotM = slotGain.getSlotMachine();
		SlotGain anotherSlotGain = getSlotGainwithIdDifferent(slotM, date);
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		if(amount == null || amount<=0) {
			errors.rejectValue("amount", REQUIRED + "No puede ser nulo ni negativo", REQUIRED + "No puede ser nulo ni negativo");
		}
		if(slotM == null) {
			errors.rejectValue("slotMachine", REQUIRED, REQUIRED);
		}
		if(anotherSlotGain.getDate()!=null) {
			errors.rejectValue("date", "Ya existe un registro para esta SlotMachine hoy", "Ya existe un registro para esta SlotMachine hoy");
		}
	}

}
