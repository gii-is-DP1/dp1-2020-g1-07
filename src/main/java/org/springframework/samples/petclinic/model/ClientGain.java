package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "clientGain")
public class ClientGain extends NamedEntity{

	@NotNull
	private Double amount;
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
	private LocalDate date;
	
	@OneToMany
	@JoinColumn(name = "dni")
	private String dni;
	
	@OneToMany
	@JoinColumn(name = "game_id")
	private Game game;
}
