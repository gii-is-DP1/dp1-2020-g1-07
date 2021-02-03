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
@Table(name = "restaurantTables")
public class RestaurantTable extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "waiter_id")
	private Waiter waiter;
	
	@NotNull
	@Min(value=2)
	private Integer size;
	
}
