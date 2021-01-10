package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("casinotables/showCasinoTableGame")
public class ShowCasinoTableGameController {
	@Autowired
	private CasinotableService castableService;
	
	@GetMapping()
	public String casinotablesListed(ModelMap modelMap) throws ParseException {
		String view = "casinotables/showCasinoTableGame";
		List<Casinotable> allCasinoTables = new ArrayList<Casinotable>(castableService.findCasinoTables());
		ArrayList<Casinotable> selectedCasinoTables = new ArrayList<Casinotable>();		
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");	
		Calendar calendario = Calendar.getInstance();
		int hour, minutes, seconds;
		hour =calendario.get(Calendar.HOUR_OF_DAY);
		minutes = calendario.get(Calendar.MINUTE);
		seconds = calendario.get(Calendar.SECOND);
		String actualTime=hour+":"+minutes+":"+seconds;
		for(Casinotable casinotable:allCasinoTables) {
			try {
				LocalDate actualDate=LocalDate.now();
				LocalDate date=casinotable.getDate();

				
				java.util.Date actualTimeFormat =(java.util.Date)format.parse(actualTime);
			    LocalTime actualTimeLastFormat = LocalTime.ofInstant(actualTimeFormat.toInstant(),ZoneId.systemDefault());
			    
			    java.util.Date startTime =(java.util.Date)format.parse(casinotable.getStartTime());
			    LocalTime startTimeFormat = LocalTime.ofInstant(startTime.toInstant(),ZoneId.systemDefault());
			    
			    java.util.Date endingTime =(java.util.Date)format.parse(casinotable.getEndingTime());
			    LocalTime endingTimeFormat = LocalTime.ofInstant(endingTime.toInstant(),ZoneId.systemDefault());
			    if(actualTimeLastFormat.isBefore(endingTimeFormat) && actualTimeLastFormat.isAfter(startTimeFormat) && date.equals(actualDate)) {
			    	selectedCasinoTables.add(casinotable);
			    }
			} catch(Exception e) {
				System.out.println(e);			
				}
		}
		modelMap.addAttribute("casinotables", selectedCasinoTables);
		return view;
	}
	
	@GetMapping(path="/Admin")
	public String casinoTablesByDay(ModelMap modelMap) {
		String vista= "casinotables/showCasinoTableGameAdmin";
		Collection<LocalDate> list=castableService.findAllDates();
		Iterable<LocalDate> dates = list;
		modelMap.addAttribute("dates", dates);
		return vista;
	}
	@ResponseBody
	@RequestMapping(value = "/Admin/{date}", method = RequestMethod.GET)
	public String loadCasinoTablesByDate(@PathVariable("date")String datestr) {
		String json = "[{";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<Casinotable> casinoTables = new ArrayList<Casinotable>(castableService.findCasinoTablesByDate(date));
			for(Casinotable casinoTable:casinoTables) {
				json = json + "\"id\":" + casinoTable.getId() +","          
						+ "\"name\":\"" + casinoTable.getName() +"\","
						+ "\"game\":{" 
							+"\"id\":" + casinoTable.getGame().getId() + ","
							+"\"name\":\"" + casinoTable.getGame().getName() + "\","
							+"\"maxPlayers\":\"" + casinoTable.getGame().getMaxPlayers() + "\","
							+"\"gameType\":\"" + casinoTable.getGame().getGametype() + "\"},"
						+ "\"gameType\":\"" + casinoTable.getGametype() +"\","
						+ "\"skill\":\"" + casinoTable.getSkill() +"\","
						+ "\"date\":\"" + casinoTable.getDate() +"\","
						+ "\"startTime\":\"" + casinoTable.getStartTime() +"\","
						+ "\"endingTime\":\"" + casinoTable.getEndingTime() +"\"},{";

				if(casinoTables.indexOf(casinoTable)==casinoTables.size()-1) {
					json = json.substring(0, json.length() - 2)+"]";
				}
			}
			if(casinoTables.size()==0) {
				json = json.substring(0, json.length() - 1);
			}
		}catch(Exception e) {
			System.out.println(castableService.findCasinoTablesByDate(date));
		}
		return json;
	}

}