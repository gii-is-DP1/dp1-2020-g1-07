package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.regex.Pattern;

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

	private boolean isNotMultiple(Integer amount) {
		
		return true;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ClientGain cgain = (ClientGain) target;
		Integer amount = cgain.getAmount();
		LocalDate date = cgain.getDate();
		Client client = cgain.getClient();
		Game game = cgain.getGame();
		
		//Amount validation
		if (amount == null || isNotMultiple(amount)) {
			errors.rejectValue("amount", REQUIRED + "to be a multiple of 5", REQUIRED + "to be a multiple of 5");
		}
		
		//Date validation
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		
		//Client validation
		if (client == null) {
			errors.rejectValue("client", REQUIRED, REQUIRED);
		}
		
		//Game validation
		if (game == null) {
			errors.rejectValue("game", REQUIRED, REQUIRED);
		}
		
	}
}
