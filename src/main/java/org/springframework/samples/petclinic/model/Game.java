package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends NamedEntity {
	
	private String name;
	private Integer maxPlayers;
	@ManyToOne
    @JoinColumn(name = "gametype_id")
	private GameType gametype;
	
	
}
