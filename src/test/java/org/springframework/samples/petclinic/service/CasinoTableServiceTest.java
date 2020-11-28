package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Casinotable;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CasinoTableServiceTest {
	
	@Autowired
	private CasinotableService casTabService;
	
	/*@Test
	public void testCountWithInitialData() {
		int count= casTabService.casinoTableCount();
		assertEquals(count,1);
	}*/
	@Test
	void shouldFindCasinotable() {
		List<Casinotable> casinotables = StreamSupport.stream(this.casTabService.findAll().spliterator(), false).collect(Collectors.toList());

		Casinotable casinotable = EntityUtils.getById(casinotables, Casinotable.class, 1);
		assertThat(casinotable.getId()).isEqualTo(1);
		assertThat(casinotable.getGame().getId()).isEqualTo(1);
		assertThat(casinotable.getGametype().getId()).isEqualTo(2);
	}
}

