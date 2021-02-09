package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {
	
	@Mock
	private ScheduleRepository scheduleRepo;
	
	protected ScheduleService scheduleService;
	
	@BeforeEach
    void setup() {
		scheduleService = new ScheduleService(scheduleRepo);
    }
	
	@Test
	public void testCountWithInitialData() {
		int count= scheduleService.scheduleCount();
		assertEquals(count,0);
	}
	
	@Test
	void testAddingSchedule() {
		Schedule new_schedule = new Schedule();
		new_schedule.setId(1);;
		Shift shift = new Shift();
		shift.setId(1);
		shift.setName("Day");
		new_schedule.setShift(shift);
		Employee employee = new Employee();
		employee.setDni("11122233A");
		employee.setId(1);
		employee.setName("Alberto Garcia");
		employee.setPhone_number("111222333");
		User usuario = new User();
		employee.setUser(usuario);
		new_schedule.setEmp(employee);
		new_schedule.setDate(LocalDate.of(2021, 9, 11));
		Collection<Schedule> sampleSchedules = new ArrayList<Schedule>();
		sampleSchedules.add(new_schedule);
        when(scheduleRepo.findAll()).thenReturn(sampleSchedules);
		
        
		List<Schedule> schedules = StreamSupport.stream(this.scheduleRepo.findAll().spliterator(), false).collect(Collectors.toList());
		Schedule saved_schedule = schedules.get(schedules.size()-1);
		assertTrue(saved_schedule.getId()==1);
		assertTrue(saved_schedule.getShift().getName().equals("Day"));
		assertTrue(saved_schedule.getEmp().getName().equals("Alberto Garcia"));
		assertTrue(saved_schedule.getDate().equals(LocalDate.of(2021, 9, 11)));
	}
	
	
}
