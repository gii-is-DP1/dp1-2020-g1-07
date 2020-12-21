package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Stage;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StageServiceTest {

	@Autowired
	private StageService stageService;
	@Test
	void shouldFindStage() {
		List<Stage> stages = StreamSupport.stream(this.stageService.findAll().spliterator(), false).collect(Collectors.toList());

		Stage stage = EntityUtils.getById(stages, Stage.class, 1);
		assertThat(stage.getId()).isEqualTo(1);
		assertThat(stage.getCapacity()).isEqualTo(40);
		assertThat(stage.getEvent_id()).isEqualTo(1);
	}
}
