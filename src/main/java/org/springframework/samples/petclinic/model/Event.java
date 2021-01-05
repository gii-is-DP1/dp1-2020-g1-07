package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends NamedEntity{
	
	
	private String name;
	@DateTimeFormat(pattern= "yyyy/MM/dd")
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "showtype_id")
	private ShowType showtype_id;
	
	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist_id;
}
