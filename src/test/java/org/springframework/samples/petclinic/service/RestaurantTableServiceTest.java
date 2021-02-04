package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.repository.RestaurantTableRepository;

@ExtendWith(MockitoExtension.class)
public class RestaurantTableServiceTest {
	
	@Mock
	private RestaurantTableRepository restauranttableRepo;
	
	protected RestaurantTableService restauranttableService;
	
	@BeforeEach
    void setup() {
		restauranttableService = new RestaurantTableService(restauranttableRepo);
    }
	
	@Test
	public void testCountWithInitialData() {
		int count= restauranttableService.restaurantTableCount();
		assertEquals(count,0);
	}
	
	/*@Test
	void testAddingRestaurantTable() {
		RestaurantTable new_rt = new RestaurantTable();
		Waiter waiter1 = new Waiter();
		waiter1.setId(1);
		waiter1.setDni("11111111A");
		waiter1.setName("William Carter");
		waiter1.setPhone_number("111222333");
		new_rt.setWaiter(waiter1);
		new_rt.setSize(3);
		Collection<RestaurantTable> sampleRestaurantTables = new ArrayList<RestaurantTable>();
		sampleRestaurantTables.add(new_rt);
        when(restauranttableRepo.findAll()).thenReturn(sampleRestaurantTables);
		
        
		List<RestaurantTable> restaurantTables = StreamSupport.stream(this.restauranttableService.findAll().spliterator(), 
				false).collect(Collectors.toList());
		
		RestaurantTable saved_rt = restaurantTables.get(0);
		assertTrue(saved_rt.getSize()==3);
		assertTrue(saved_rt.getWaiter().getDni().equals("11111111A"));
		
	}*/

}
