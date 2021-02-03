package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.RestaurantReservation;
import org.springframework.samples.petclinic.model.RestaurantTable;
import org.springframework.samples.petclinic.model.TimeInterval;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Waiter;
import org.springframework.samples.petclinic.repository.RestaurantReservationRepository;

@ExtendWith(MockitoExtension.class)
public class RestaurantReservationServiceTest {

	@Mock
	private RestaurantReservationRepository reservationRepo;
	
	protected RestaurantReservationService reservationService;
	
	@BeforeEach
    void setup() {
		reservationService = new RestaurantReservationService(reservationRepo);
    }
	
	@Test
	void testNewReservation() {
		RestaurantReservation new_reservation = new RestaurantReservation();
		new_reservation.setId(1);
		Client client = new Client();
		client.setDni("11122233A");
		client.setId(1);
		client.setName("Jacobo García");
		client.setPhone_number("123456789");
		client.setUser(new User());
		new_reservation.setClient(client);
		LocalDate date = LocalDate.of(2021, 02, 15);
		new_reservation.setDate(date);
		RestaurantTable restauranttable = new RestaurantTable();
		restauranttable.setId(1);
		restauranttable.setSize(8);
		restauranttable.setWaiter(new Waiter());
		new_reservation.setRestauranttable(restauranttable);
		TimeInterval timeInterval = new TimeInterval();
		timeInterval.setId(1);
		timeInterval.setName("13:00-14:00");
		new_reservation.setTimeInterval(timeInterval);
		Collection<RestaurantReservation> sampleReservations = new ArrayList<RestaurantReservation>();
		sampleReservations.add(new_reservation);
        when(reservationRepo.findAll()).thenReturn(sampleReservations);
		
        
		List<RestaurantReservation> reservations = StreamSupport.stream(this.reservationRepo.findAll().spliterator(), false).collect(Collectors.toList());
		RestaurantReservation saved_reservations = reservations.get(reservations.size()-1);
		assertTrue(saved_reservations.getId()==1);
		assertTrue(saved_reservations.getClient().getDni().equals("11122233A"));
		assertTrue(saved_reservations.getDate().getDayOfMonth()==15);
		assertTrue(saved_reservations.getRestauranttable().getSize()==8);
		assertTrue(saved_reservations.getTimeInterval().getName().equals("13:00-14:00"));

	}
	
}
