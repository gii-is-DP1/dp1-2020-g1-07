package org.springframework.samples.petclinic.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class Week implements Comparable<Week>{
	private LocalDate monday;
	private LocalDate sunday;
	private String text;
	
	private final DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yy");
	
	public Week(LocalDate day) {
		this.monday = day.with(DayOfWeek.MONDAY);
		this.sunday = this.monday.plusDays(6);
		this.text = this.monday.format(form) + " to " + this.sunday.format(form);
	}

	public boolean hasDay(LocalDate day) {
		if (day.with(DayOfWeek.MONDAY).equals(this.monday))
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(Week arg0) {
		if (arg0.monday.equals(this.monday))
			return 0;
		else if (arg0.monday.isAfter(this.monday))
			return -1;
		else
			return 1;
	}
}
