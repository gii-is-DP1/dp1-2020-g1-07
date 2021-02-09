package org.springframework.samples.petclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/workers")
public class WorkerController {
	
	@GetMapping()
	public String listChefs(ModelMap modelMap) {
		log.info("Loading worker pad.");
		String view= "workers/worker";
		return view;
	}
	

}