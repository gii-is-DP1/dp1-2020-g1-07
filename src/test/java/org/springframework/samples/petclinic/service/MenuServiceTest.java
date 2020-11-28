package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.samples.petclinic.model.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MenuServiceTest {

	@Autowired
	protected MenuService menuService;

	@Test
	void shouldFindMenus() {
		List<Menu> menus = StreamSupport.stream(this.menuService.findAll().spliterator(), false).collect(Collectors.toList());

		Menu menu = EntityUtils.getById(menus, Menu.class, 1);
		assertThat(menu.getDate().isEqual(LocalDate.of(2020, 11, 28)));
		assertThat(menu.getFirst_dish().getId()==1);
		assertThat(menu.getSecond_dish().getId()==2);
		assertThat(menu.getDessert().getId()==3);
		assertThat(menu.getShift().getId()).isEqualTo(2);
	}

}
