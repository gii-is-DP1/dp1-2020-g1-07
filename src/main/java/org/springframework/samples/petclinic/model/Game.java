package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends NamedEntity {
	
	@NotBlank
	private String name;
	
	@NotNull
	@Min(value = 1)
	private Integer maxPlayers;
	
	@ManyToOne
    @JoinColumn(name = "gametype_id")
	private GameType gametype;
	
	
}
