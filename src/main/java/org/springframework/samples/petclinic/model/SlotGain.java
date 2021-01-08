package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="slotgains")
public class SlotGain extends NamedEntity{
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
    private LocalDate date;
	
	@NotNull
	private Integer amount;
	
	@ManyToOne
    @JoinColumn(name = "slot_machine_id")
	private SlotMachine slotMachine;

}
