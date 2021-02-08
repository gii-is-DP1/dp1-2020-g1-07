package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "slotgames", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Slotgame extends NamedEntity{
	
	@NotBlank
	private String name;
	
	@NotNull
	@Min(value = 0)
	private Integer jackpot;

}
