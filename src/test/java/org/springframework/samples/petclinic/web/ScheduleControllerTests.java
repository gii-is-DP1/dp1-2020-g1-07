package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Shift;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ScheduleService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= ScheduleController.class,
includeFilters= {@ComponentScan.Filter(value = EmployeeFormatter.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = ShiftFormatter3.class, type = FilterType.ASSIGNABLE_TYPE ),
				@ComponentScan.Filter(value = ScheduleValidator.class, type = FilterType.ASSIGNABLE_TYPE )},
excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ScheduleControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ScheduleService scheduleService;
	
	private Schedule schedule;
	
	@BeforeEach
	void setup() {

		//Hay que preparar los dos enumerados y despues un plato de prueba
		Shift shift1 = new Shift();
		shift1.setName("Day");
		shift1.setId(1);
		Shift shift2 = new Shift();
		shift2.setName("Afternoon");
		shift2.setId(2);
		Shift shift3 = new Shift();
		shift3.setName("Night");
		shift3.setId(3);
		List<Shift> shifts = new ArrayList<Shift>();
		shifts.add(shift1);
		shifts.add(shift2);
		shifts.add(shift3);
		given(this.scheduleService.findShifts()).willReturn(shifts);
		
		Employee employee = new Employee();
		employee.setDni("11122233A");
		employee.setId(1);
		employee.setName("Alberto Garcia");
		employee.setPhone_number("111222333");
		User usuario = new User();
		usuario.setUsername("user");
		employee.setUser(usuario);
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		List<String> dnis = new ArrayList<String>();
		dnis.add("11122233A");
		given(this.scheduleService.findEmployees()).willReturn(employees);
		given(this.scheduleService.findEmployeeByUsername("user")).willReturn(employee);
		given(this.scheduleService.findScheduleByDni()).willReturn(dnis);
		
		schedule = new Schedule();
		schedule.setId(1);
		schedule.setShift(shift1);
		schedule.setEmp(employee);
		schedule.setDate(LocalDate.of(2021, 02, 15));
		given(this.scheduleService.findScheduleById(1)).willReturn(Optional.of(schedule));
		List<Schedule> schedules = new ArrayList<Schedule>();
		schedules.add(schedule);
		given(this.scheduleService.findAll()).willReturn(schedules);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/schedules/new")).andExpect(status().isOk()).andExpect(model().attributeExists("schedule"))
		.andExpect(view().name("schedules/addSchedule"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/schedules/save").param("date", "2021/02/10")
						.with(csrf())
						.param("emp", "11122233A")
						.param("shift", "Day"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("schedules/listSchedule"));
	}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/schedules/save")
						.with(csrf())
						.param("emp", "11122233A")
						.param("shift", "Day"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("schedule"))
			.andExpect(model().attributeHasFieldErrors("schedule", "date"))
			.andExpect(view().name("schedules/addSchedule"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormRepeatedDate() throws Exception {
		mockMvc.perform(post("/schedules/save").param("date", "2021/02/15")
						.with(csrf())
						.param("emp", "11122233A")
						.param("shift", "Day"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("schedule"))
			.andExpect(model().attributeHasFieldErrors("schedule", "date"))
			.andExpect(view().name("schedules/addSchedule"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateScheduleForm() throws Exception {
		mockMvc.perform(get("/schedules/{scheduleId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("schedule"))
				.andExpect(model().attribute("schedule", hasProperty("date", is(LocalDate.of(2021, 02, 15)))))
				.andExpect(model().attribute("schedule", hasProperty("emp", is(schedule.getEmp()))))
				.andExpect(model().attribute("schedule", hasProperty("shift", is(schedule.getShift()))))
				.andExpect(view().name("schedules/updateSchedule"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateScheduleFormSuccess() throws Exception {
		mockMvc.perform(post("/schedules/{scheduleId}/edit", 1).param("date", "2021/02/25")
					.with(csrf())
					.param("emp", "11122233A")
					.param("shift", "Day"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/schedules"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormRepeatedDate() throws Exception {
		mockMvc.perform(post("/schedules/{scheduleId}/edit", 2).param("date", "2021/02/15")
				.with(csrf())
				.param("emp", "11122233A")
				.param("shift", "Day"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeHasErrors("schedule"))
			.andExpect(model().attributeHasFieldErrors("schedule", "date"))
			.andExpect(view().name("schedules/updateSchedule"));
	}
	
}
