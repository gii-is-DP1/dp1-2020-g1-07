package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "restaurantTables")
public class RestaurantTable extends BaseEntity{
	
	@ManyToMany(mappedBy = "serves")
	private Set<Waiter> waiters;
	
	@NotNull
	@Min(value=2)
	private Integer size;
	
}
