package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stage extends BaseEntity{
	
	@NotNull
	@Min(value = 1)
	private Integer capacity;
	
	
	
}
