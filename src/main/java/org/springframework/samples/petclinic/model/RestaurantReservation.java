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
@Table(name="restaurantreservations")
public class RestaurantReservation extends BaseEntity{
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "restauranttable_id")
	private RestaurantTable restauranttable;
	
	@NotNull
	@DateTimeFormat(pattern= "yyyy/MM/dd")
    private LocalDate date;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "time_interval_id")
	private TimeInterval timeInterval;
}
