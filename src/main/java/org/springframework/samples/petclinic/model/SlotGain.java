package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name="slotgains")
public class SlotGain extends NamedEntity{
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
    private LocalDate date;
	
	@NotNull
	private Integer amount;

}
