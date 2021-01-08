package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Casinotable extends NamedEntity{
	
	@NotEmpty
	private String name;
	@ManyToOne
    @JoinColumn(name = "game_id")
	private Game game;
	@ManyToOne
    @JoinColumn(name = "gametype_id")
	private GameType gametype;
	@ManyToOne
    @JoinColumn(name = "skill_id")
	private Skill skill;
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
	private LocalDate date;
	@NotEmpty
	@Pattern(regexp="^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$")
	private String  startTime;
	@NotEmpty
	@Pattern(regexp="^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$")
	private String  endingTime;
}