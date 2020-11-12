package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;



@Data
@Entity

public class Game extends NamedEntity {
	
	private String name;
	private Integer maxPlayers;
	@ManyToOne
    @JoinColumn(name = "type_game_id")
	private GameType typeGame;
	
	
}
