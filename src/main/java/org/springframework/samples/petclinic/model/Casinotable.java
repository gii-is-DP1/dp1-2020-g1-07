package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity

public class Casinotable extends NamedEntity{

	private Integer id;
	@ManyToOne
    @JoinColumn(name = "game_id")
	private Game game_id;
	@ManyToOne
    @JoinColumn(name = "gametype_id")
	private GameType gametype_id;
	@ManyToOne
    @JoinColumn(name = "skill_id")
	private SkillLevel skill_id;
	
}
