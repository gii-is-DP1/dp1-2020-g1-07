package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.SlotGain;
import org.springframework.samples.petclinic.model.SlotMachine;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SlotGainServiceTests {
	@Autowired
	protected SlotGainService slotGainService;
	
	@Test
	public void testCountWithInitialData() {
		int count= slotGainService.slotGainCount();
		assertEquals(count,9);
	}
	//INSERT INTO slotgains VALUES (1,'1',100,'2010-09-07',1);
	@Test
	void shouldFindSlotGains() {
		List<SlotGain> slotgains = StreamSupport.stream(this.slotGainService.findAll().spliterator(), false).collect(Collectors.toList());
		
		SlotGain slotGain = EntityUtils.getById(slotgains, SlotGain.class, 2);
		assertThat(slotGain.getDate().equals(LocalDate.of(2010, 9, 07)));
		assertThat(slotGain.getSlotMachine().equals(1));
		assertThat(slotGain.getAmount().equals(100));

	}
}
