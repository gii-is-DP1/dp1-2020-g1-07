package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends NamedEntity{
	
	@NotBlank
	private String name;
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
	private LocalDate date;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "showtype_id")
	private ShowType showtype_id;
	
	@ManyToMany(mappedBy = "acts")
	private Set<Artist> artists;
	
	@ManyToOne
	@JoinColumn(name = "stage_id")
	private Stage stage_id;
	
}
