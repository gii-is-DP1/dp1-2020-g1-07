package org.springframework.samples.petclinic.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Week {
	public LocalDate monday;
	public LocalDate sunday;
	public String text;
	
	private final DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/mm/yy");
	
	public Week(LocalDate day) {
		this.monday = day.with(DayOfWeek.MONDAY);
		this.sunday = this.monday.plusDays(6);
		this.text = this.monday.format(form) + " al " + this.sunday.format(form);
	}
	
	public boolean hasDay(LocalDate day) {
		if (day.with(DayOfWeek.MONDAY).equals(this.monday))
			return true;
		else
			return false;
	}
}
