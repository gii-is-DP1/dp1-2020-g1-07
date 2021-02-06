package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.RestaurantReservationService;
import org.springframework.samples.petclinic.service.RestaurantTableService;
import org.springframework.samples.petclinic.service.SlotMachineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/finance")
public class FinanceController {
	@Autowired
	private SlotMachineService slotMachineService;

	
}
