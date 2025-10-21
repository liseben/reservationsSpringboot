package projet.pid.reservationsspringboot.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}