package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artist;
import org.springframework.samples.petclinic.service.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artists")
public class ArtistController {
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private ArtistValidator validator;
	
	@InitBinder("artist")
	public void initArtistBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(validator);
	}
	
	@GetMapping()
	public String listArtists(ModelMap modelMap) {
		String view= "artists/listArtist";
		Iterable<Artist> artists=artistService.findAll();
		modelMap.addAttribute("artists", artists);
		return view;
	}
	
	@GetMapping(path="/new")
	public String createArtist(ModelMap modelMap) {
		String view="artists/addArtist";
		modelMap.addAttribute("artist", new Artist());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveArtist(@Valid Artist artist, BindingResult result, ModelMap modelMap) {
		String view="artists/listArtist";
		if(result.hasErrors()) {
			modelMap.addAttribute("artist", artist);
			return "artists/addArtist";
			
		}else {
			if (validator.getArtistwithIdDifferent(artist.getDni(), null)) {
				result.rejectValue("dni", "dni.duplicate", "Artist with dni" + artist.getDni() + "already in database");
				modelMap.addAttribute("artist", artist);
				return "artists/addArtist";
			}
			artistService.save(artist);
			
			modelMap.addAttribute("message", "Artist successfully saved!");
			view=listArtists(modelMap);
		}
		return view;
	}
	
	@GetMapping(path="/delete/{artistId}")
	public String deleteArtist(@PathVariable("artistId") int artistId, ModelMap modelMap) {
		String view="artists/listArtist";
		Optional<Artist> artist = artistService.findArtistById(artistId);
		if(artist.isPresent()) {
			artistService.delete(artist.get());
			modelMap.addAttribute("message", "Artist successfully deleted!");
			view=listArtists(modelMap);
		}else {
			modelMap.addAttribute("message", "Artist not found!");
			view=listArtists(modelMap);
		}
		return view;
	}
	
	@GetMapping(value = "/{artistId}/edit")
    public String initUpdateArtistForm(@PathVariable("artistId") int artistId, ModelMap model) {
		Artist artist = artistService.findArtistById(artistId).get();
        model.put("artist", artist);
        return "artists/updateArtist";
    }

    @PostMapping(value = "/{artistId}/edit")
    public String processUpdateArtistForm(@Valid Artist artist, BindingResult result,
            @PathVariable("artistId") int artistId, ModelMap model) {
        if (result.hasErrors()) {
            model.put("artist", artist);
            return "artists/updateArtist";
        }
        else {
        	if (validator.getArtistwithIdDifferent(artist.getDni(), artist.getId())) {
				result.rejectValue("dni", "dni.duplicate", "Artist with dni" + artist.getDni() + "already in database");
				model.addAttribute("artist", artist);
				return "artists/updateArtist";
			}
        	artist.setId(artistId);
            this.artistService.save(artist);
            return "redirect:/artists";
        }
    }
}
