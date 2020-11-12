package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Game;
import org.springframework.samples.petclinic.model.GameType;
import org.springframework.samples.petclinic.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class GameService {
	@Autowired
	private  GameRepository gameRepo; 
	
	@Transactional
	public int gameCount() {
		return (int)gameRepo.count();
	}
	
	@Transactional
	public Iterable<Game> findAll() {
		return gameRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public  Optional<Game> findGameById(int id){ 
		return gameRepo.findById(id);
	}

	@Transactional
	public  void save(Game game) {  
		gameRepo.save(game);
	}

	public  void delete(Game game) {
		gameRepo.delete(game);
	}
	public Collection<GameType> findGameTypes() throws DataAccessException{
        // TODO Auto-generated method stub
        return gameRepo.findGameTypes();
    }
}
