package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClientGainValidator implements Validator{

	private static final String REQUIRED = "required";

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ClientGain.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ClientGain cgain = (ClientGain) target;
		Integer amount = cgain.getAmount();
		LocalDate date = cgain.getDate();
		Client client = cgain.getClient();
		Game game = cgain.getGame();
		Integer table = cgain.getTableId();
		//Amount validation
		if (amount == null || amount%5!=0) {
			errors.rejectValue("amount", "Amount can not be empty and must be a multiple of 5", "Amount can not be empty and must be a multiple of 5");
		}
		
		//Date validation
		if (date == null) {
			errors.rejectValue("date", "Date can not be null", "Date can not be null");
		}
		
		//Client validation
		if (client == null) {
			errors.rejectValue("client", "Client can not be null", "Client can not be null");
		}
		
		//Game validation
		if (game == null) {
			errors.rejectValue("game", "Game can not be null", "Game can not be null");
		}
		
		//Table validation
		if (table == null) {
			errors.rejectValue("tableId", "Table can not be null", "Table can not be null");
		}
		
	}
}
