package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Boolean getUserwithIdDifferent(String username) {
		Boolean result = false;
		List<User> users = StreamSupport.stream(this.userService.findAll().spliterator(), false).collect(Collectors.toList());
		for (User us : users) {
			String name = us.getUsername();
			if (name.equals(username)) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		User us = (User) obj;
		String name = us.getUsername();
		String pass = us.getPassword();
		// username validation
		if (name == null || name.trim().equals("")) {
			errors.rejectValue("username", REQUIRED, REQUIRED);
		}
		// password validation
		if (pass == null || pass.trim().equals("")) {
			errors.rejectValue("password", REQUIRED, REQUIRED);
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}
}
