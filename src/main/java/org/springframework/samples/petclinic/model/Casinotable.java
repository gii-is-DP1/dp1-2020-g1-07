package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "casinotables")
public class Casinotable extends NamedEntity{
	
	@NotBlank
	private String name;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "game_id")
	private Game game;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "gametype_id")
	private GameType gametype;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "skill_id")
	private Skill skill;
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
	private LocalDate date;
	
	@NotBlank
	@Pattern(regexp="^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$")
	private String  startTime;
	
	@NotBlank
	@Pattern(regexp="^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$")
	private String  endingTime;
}