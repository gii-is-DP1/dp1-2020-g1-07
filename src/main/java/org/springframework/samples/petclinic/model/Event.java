package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class Event extends NamedEntity{
	
	private Integer id;
	
	private String name;
	
	private String day;
	
	@ManyToOne
	@JoinColumn(name = "showtype_id")
	private ShowType showtype_id;
	
	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist_id;
}
