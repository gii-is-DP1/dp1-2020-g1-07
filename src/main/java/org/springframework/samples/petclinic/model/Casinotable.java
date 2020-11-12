package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity

public class Casinotable extends NamedEntity{

	private String name;
	@ManyToOne
    @JoinColumn(name = "type_game_id")
	private GameType typeGame;
	private SkillLevel skill;
	
}
