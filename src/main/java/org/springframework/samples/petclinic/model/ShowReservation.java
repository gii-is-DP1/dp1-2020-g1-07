package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "showreservations")
public class ShowReservation extends BaseEntity{

	@NotNull
	@Min(value = 1)
	private Integer seats;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
}
