package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity

public class Game extends NamedEntity {
	
	private String name;
	private Integer maxPlayers;
	private GameType typeGame;
	
}
