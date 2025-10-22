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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.access.prepost.PreAuthorize;

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

    // Créer un nouvel artiste : ADMIN seulement
    @GetMapping("/artists/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(Model model) {
        // Ne créer une instance Artist que si elle n'existe pas déjà
        if (!model.containsAttribute("artist")) {
            model.addAttribute("artist", new Artist());
        }
        return "artist/create";
    }

    @PostMapping("/artists/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String store(@Valid @ModelAttribute Artist artist, BindingResult bindingResult,
            Model model, RedirectAttributes redirAttrs) {

        // Si erreur de validation : afficher le formulaire
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la création de l'artiste !");
            return "artist/create";
        }

        // Créer l'artiste
        service.addArtist(artist);

        // Ajouter message flash et rediriger
        redirAttrs.addFlashAttribute("successMessage", "Artiste créé avec succès.");
        return "redirect:/artists/" + artist.getId();
    }

    
    
    @GetMapping("/artists/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@Valid @ModelAttribute Artist artist,
            BindingResult bindingResult,
            @PathVariable int id,
            Model model, RedirectAttributes redirAttrs) {

        // Si erreur de validation : afficher le formulaire avec les erreurs
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la modification de l'artiste !");
            return "artist/edit";
        }

        // Vérifier que l'artiste existe
        Artist existing = service.getArtist(id);
        if (existing == null) {
            return "artist/index";
        }

        // Mettre à jour et ajouter message flash
        service.updateArtist(id, artist);
        redirAttrs.addFlashAttribute("successMessage", "Artiste modifié avec succès.");
        return "redirect:/artists/" + artist.getId();
    }

    @DeleteMapping("/artists/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable int id, Model model, RedirectAttributes redirAttrs) {

        // Récupérer l'artiste à supprimer
        Artist existing = service.getArtist(id);

        // Toujours rediriger (message flash dans tous les cas)
        if (existing != null) {
            service.deleteArtist(id);
            redirAttrs.addFlashAttribute("successMessage", "Artiste supprimé avec succès.");
        } else {
            redirAttrs.addFlashAttribute("errorMessage", "Échec de la suppression de l'artiste !");
        }

        return "redirect:/artists";
    }
}