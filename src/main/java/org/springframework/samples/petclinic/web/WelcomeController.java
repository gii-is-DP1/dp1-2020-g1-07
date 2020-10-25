package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
		List<Person> list = new ArrayList<Person>();
		Person bea = new Person();
		bea.setFirstName("Beatriz");
		bea.setLastName("Beltrán");
		list.add(bea);
		Person david = new Person();
		david.setFirstName("David");
		david.setLastName("Barragan");
		list.add(david);
		Person gonza = new Person();
		gonza.setFirstName("Antonio");
		gonza.setLastName("Gonzalez");
		list.add(gonza);
		Person danicaro = new Person();
		danicaro.setFirstName("Daniel");
		danicaro.setLastName("Caro");
		list.add(danicaro);
		Person vicente = new Person();
		vicente.setFirstName("Vicente");
		vicente.setLastName("Soria");
		list.add(vicente);
		Person danimunoz = new Person();
		danimunoz.setFirstName("Daniel");
		danimunoz.setLastName("Munyoz");
		list.add(danimunoz);
		model.put("persons", list);
		model.put("title", "DP1 Project");
		model.put("group", "Teachers");
		return "welcome";
	  }
}
