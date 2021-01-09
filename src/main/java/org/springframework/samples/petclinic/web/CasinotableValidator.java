package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CasinotableValidator implements Validator{
	private static final String REQUIRED = "required";

	
	public Boolean getReverseDate(String startTime, String endingTime) throws ParseException {
		Boolean res=false;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");	
		java.util.Date startTimeFor =(java.util.Date)format.parse(startTime);
	    LocalTime startTimeFormat = LocalTime.ofInstant(startTimeFor.toInstant(),ZoneId.systemDefault());
	    java.util.Date endingTimeFor =(java.util.Date)format.parse(endingTime);
	    LocalTime endingTimeFormat = LocalTime.ofInstant(endingTimeFor.toInstant(),ZoneId.systemDefault());
		if (startTimeFormat.isAfter(endingTimeFormat)) {
			    res=true;
				return res;
				
		}
	    System.out.print("EL RESULTADO ES: "+res+"\n");

		return res;
	}
	@Override
	public void validate(Object obj, Errors errors) {
		Casinotable casinotable = (Casinotable) obj;
		Game game = casinotable.getGame();
		GameType gametype = casinotable.getGametype();
		Skill skill = casinotable.getSkill();
		if (game == null || game.getName().trim().equals("")) {
			errors.rejectValue("game", REQUIRED, REQUIRED);
		}
		if (gametype == null ||gametype.getName() == null || gametype.getName().trim().equals("")) {
			errors.rejectValue("gametype", REQUIRED, REQUIRED);
		}
		if (skill == null||skill.getName() == null || skill.getName().trim().equals("")  ) {
			errors.rejectValue("skill", REQUIRED, REQUIRED);
		}
	}
	@Override
		
	public boolean supports(Class<?> clazz) {
		return Casinotable.class.isAssignableFrom(clazz);
	}
}
	