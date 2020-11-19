package org.springframework.samples.petclinic.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Integer>{

}
