package org.springframework.samples.petclinic.web;

import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.service.ArtistService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ArtistValidator implements Validator{
	private static final String REQUIRED = "required";

	@Autowired
	private ArtistService artistService;
	
	public boolean getArtistwithIdDifferent(String dni, Integer id) {
		if (StreamSupport.stream(this.artistService.findAll().spliterator(), false)
				.filter(x -> (x.getDni().equals(dni) && id == null) || 
						(x.getDni().equals(dni) && id != null && !x.getId().equals(id)))
				.findAny().isPresent())
			return true;
		else
			return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Artist artist = (Artist) target;
		String dni = artist.getDni();
		String name = artist.getName();
		String phone_number = artist.getPhone_number();
		
		//DNI validation
		if (dni == null || dni.trim().equals("") || !Pattern.matches("[0-9]{8}[A-Z]{1}", dni)) {
			errors.rejectValue("dni", REQUIRED + " to contain 8 digits and a single capital letter",
					REQUIRED + " to contain 8 digits and a single capital letter");
		}
		
		//Name validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}
		
		//Phone validation
		if (phone_number == null || phone_number.trim().equals("") || !Pattern.matches("[0-9]{9}", phone_number)) {
			errors.rejectValue("phone_number", REQUIRED + " to contain 9 digits",
					REQUIRED + " to contain 9 digits");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Artist.class.isAssignableFrom(clazz);
	}

	
}
