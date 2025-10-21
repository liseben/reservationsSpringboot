package projet.pid.reservationsspringboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import projet.pid.reservationsspringboot.model.Artist;
import projet.pid.reservationsspringboot.service.ArtistService;

@Controller
public class ArtistController {

    @Autowired
    private ArtistService service;

    @GetMapping("/artists")
    public String index(Model model) {
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("artists", artists);
        model.addAttribute("title", "Liste des artistes");
        return "artist/index";
    }

    @GetMapping("/artists/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        Artist artist = service.getArtist(id);
        model.addAttribute("artist", artist);
        model.addAttribute("title", "Fiche d'un artiste");
        return "artist/show";
    }
    

    @GetMapping("/artists/create")
    public String create(Model model) {
        Artist artist = new Artist();
        model.addAttribute("artist", artist);
        return "artist/create";
    }

    @PostMapping("/artists/create")
    public String store(@Valid @ModelAttribute Artist artist,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la création !");
            return "artist/create";
        }

        service.addArtist(artist);
        return "redirect:/artists/" + artist.getId();
    }

    @GetMapping("/artists/{id}/edit")
    public String edit(Model model, 
                      @PathVariable int id,
                      HttpServletRequest request) {
        
        Artist artist = service.getArtist(id);
        model.addAttribute("artist", artist);
        
        // Lien retour pour annulation
        String referrer = request.getHeader("Referer");
        if (referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/artists/" + artist.getId());
        }
        
        return "artist/edit";
    }
    
    @PutMapping("/artists/{id}/edit")
    public String update(@Valid @ModelAttribute Artist artist,
                        BindingResult bindingResult,
                        @PathVariable int id,
                        Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la modification !");
            return "artist/edit";
        }
        
        Artist existing = service.getArtist(id);
        if (existing == null) {
            return "redirect:/artists";
        }
        
        service.updateArtist(id, artist);
        return "redirect:/artists/" + artist.getId();
    }
}