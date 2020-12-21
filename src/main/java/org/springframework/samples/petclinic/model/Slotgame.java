package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "slotgames", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Slotgame extends NamedEntity{
	
	@NotNull
	private String name;
	
	@NotNull
	private Integer jackpot;

}
