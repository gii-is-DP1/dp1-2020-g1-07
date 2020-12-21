package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.ClientGain;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
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
		String dni = cgain.getDni();
		String game = cgain.getGame();
		
		//Amount validation
		if (amount == null || amount < 5) {
			errors.rejectValue("amount", REQUIRED + " to be equal or larger than 5", REQUIRED + " to be equal or larger than 5");
		}
		
		//Date validation
		if (date == null) {
			errors.rejectValue("date", REQUIRED, REQUIRED);
		}
		
		//DNI validation
		if (dni == null || dni.trim().equals("") || !Pattern.matches("[0-9]{8}[A-Z]", dni)) {
			errors.rejectValue("dni", REQUIRED + " to have 8 numbers and one capital letter at the end", REQUIRED + " to have 8 numbers and one capital letter at the end");
		}
		
		//Game validation
		if (game == null || game.trim().equals("")) {
			errors.rejectValue("game", REQUIRED, REQUIRED);
		}
		
	}
}
