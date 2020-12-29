package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClientValidator implements Validator {
	
	private static final String REQUIRED = "required";
	
	@Autowired
	private ClientService clientService;
	
	public Boolean getClientwithIdDifferent(String dni) {
		dni = dni.toLowerCase();
		Boolean result = false;
		List<Client> clients = StreamSupport.stream(this.clientService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Client client : clients) {
			String compDni = client.getDni();
			compDni = compDni.toLowerCase();
			if (compDni.equals(dni)) {
				result = true;
			}
		}
		return result;
	}
	
	public Boolean getClientwithIdDifferent(String dni, Integer id) {
		dni = dni.toLowerCase();
		Boolean result = false;
		List<Client> clients = StreamSupport.stream(this.clientService.findAll().spliterator(), false).collect(Collectors.toList());
		for (Client client : clients) {
			String compDni = client.getDni();
			compDni = compDni.toLowerCase();
			if (compDni.equals(dni) && client.getId()!=id) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Client client = (Client) obj;
		String name = client.getName();
		String dni = client.getDni();
		String phoneNumber = client.getPhone_number();
		// name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}

		// dni validation
		if (dni == null || dni.trim().equals("") || !Pattern.matches("[0-9]{8}[A-Z]{1}", dni)) {
			errors.rejectValue("dni", REQUIRED + " to have 8 numbers and one capital letter at the end", REQUIRED + " to have 8 numbers and one capital letter at the end");
		}

		// phoneNumber date validation
		if (phoneNumber == null || phoneNumber.trim().equals("") || !Pattern.matches("[0-9]{9}", phoneNumber)) {
			errors.rejectValue("phone_number", REQUIRED + " to have 9 numbers", REQUIRED + " to have 9 numbers");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

}
