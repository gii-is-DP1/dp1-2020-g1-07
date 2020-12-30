package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Iterator;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.repository.ArtistRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ArtistServiceTest {

	@Mock
	private ArtistRepository artistRepo;
	
	@Autowired
	protected ArtistService artistService;
	
	@BeforeEach
    void setup() {
		Artist artist1 = new Artist();
		artist1.setId(1);
		artist1.setDni("11111111A");
		artist1.setName("William Carter");
		artist1.setPhone_number("111222333");
		
		Artist artist2 = new Artist();
		artist2.setId(2);
		artist2.setDni("22222222B");
		artist2.setName("John Titor");
		artist2.setPhone_number("444555666");
		
		Artist artist3 = new Artist();
		artist3.setId(3);
		artist3.setDni("33333333C");
		artist3.setName("Gordon Freeman");
		artist3.setPhone_number("777888999");
		
		given(this.artistRepo.findAll()).willReturn(Lists.list(artist1, artist2, artist3));
		artistService = new ArtistService(artistRepo);
    }
	
	@Test
	void findArtistTest() {
		Iterator<Artist> it = this.artistService.findAll().iterator();
		Artist artist1 = it.next();
		assertThat(artist1.getDni().equals("11111111A"));
		assertThat(artist1.getName().equals("William Carter"));
		assertThat(artist1.getPhone_number().equals("444555666"));
		
		
		assertThat(it.next().getName().equals("John Titor"));
		assertThat(it.next().getName().equals("Gordon Freeman"));
		assertThat(!it.hasNext());
	}
}
