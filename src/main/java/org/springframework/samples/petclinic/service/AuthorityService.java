package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authority;
import org.springframework.samples.petclinic.repository.AuthorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityService {

	private AuthorityRepository authRepo;

	@Autowired
	public AuthorityService(AuthorityRepository authRepo) {
		this.authRepo = authRepo;
	}

	@Transactional
	public int authCount() {
		return (int)authRepo.count();
	}

	@Transactional
	public Iterable<Authority> findAll() {
		return authRepo.findAll();
	}
	
	@Transactional
	public void save(Authority auth) throws DataAccessException {
		authRepo.save(auth);
	}
	
	@Transactional(readOnly=true)
	public Optional<Authority> findAuthorityById(int id) {
		return authRepo.findById(id);
	}
	
	public  void delete(Authority auth) { 
		authRepo.delete(auth);
	}
}
