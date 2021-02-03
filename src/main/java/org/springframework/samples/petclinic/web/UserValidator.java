package org.springframework.samples.petclinic.web;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator{
private static final String REQUIRED = "required";
	
	@Autowired
	private UserService userService;
	
	public boolean getUserwithIdDifferent(String username, Integer id) {
		if (StreamSupport.stream(this.userService.findAll().spliterator(), false)
				.filter(x -> (x.getUsername().equals(username) && id == null) || 
						(x.getUsername().equals(username) && id != null && !x.getId().equals(id)))
				.findAny().isPresent())
			return true;
		else
			return false;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		User us = (User) obj;
		String name = us.getUsername();
		String pass = us.getPassword();
		Authority auth = us.getAuthority();
		
		// username validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("username", REQUIRED, REQUIRED);
		}
		// password validation
		if (pass == null || pass.trim().equals("")) {
			errors.rejectValue("password", REQUIRED, REQUIRED);
		}
		// authority validation
		if (auth == null || !userService.findAuthorities().contains(auth)) {
			errors.rejectValue("authority", REQUIRED, REQUIRED);
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}
}
