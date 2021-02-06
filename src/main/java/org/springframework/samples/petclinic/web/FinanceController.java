package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.service.CasinotableService;
import org.springframework.samples.petclinic.service.SlotGainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/finance")
public class FinanceController {
	@Autowired
	private SlotGainService slotGainService;
	@Autowired
	private CasinotableService castableService;
	@GetMapping()
	public String casinoTablesByDay(ModelMap modelMap) {
		String vista= "finances/financeList";
		Collection<LocalDate> listSM=slotGainService.findAllDates();
		Collection<LocalDate> listCT=castableService.findAllDates();
		Collection<LocalDate> totalDates = new ArrayList<LocalDate>();
		totalDates.addAll(listSM);
		totalDates.addAll(listCT);
		Set<LocalDate> s = new LinkedHashSet<LocalDate>(totalDates);
		Collection<LocalDate> dates_no_rep = new ArrayList<LocalDate>(s);
		Iterable<LocalDate> dates = dates_no_rep;
		modelMap.addAttribute("dates", dates);
		return vista;
	}
	@ResponseBody
	@RequestMapping(value = "/finance/{date}", method = RequestMethod.GET)
	public String financeByDate(@PathVariable("date")String datestr) {
		String json = "[{";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate date = LocalDate.parse(datestr, formatter);
		try {
			List<SlotGain> slotgains = new ArrayList<SlotGain>(slotGainService.findSlotGainByDate(date));
			System.out.print(slotgains);
			for(SlotGain slotgain:slotgains) {
				List<Integer> gainsById = new ArrayList<Integer>(slotGainService.findGainsBySlotId((slotgain.getSlotMachine().getId())));
				Integer sum = 0;
	            for(int i = 0; i < gainsById.size(); i++){
	                sum=sum+gainsById.get(i);
	            }
				json = json + "\"SlotId\":" + slotgain.getSlotMachine().getId() +","          
						+ "\"SlotGains\":\"" + sum +"\"},{";
				
				if(slotgains.indexOf(slotgain)==slotgains.size()-1) {
					json = json.substring(0, json.length() - 2)+"]";
				}
			}
			if(slotgains.size()==0) {
				json = json.substring(0, json.length() - 1);
			}
		}catch(Exception e) {
			System.out.println(slotGainService.findSlotGainByDate(date));
		}
		return json;
	}
}
