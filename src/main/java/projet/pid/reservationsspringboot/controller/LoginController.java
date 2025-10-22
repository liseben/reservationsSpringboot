package projet.pid.reservationsspringboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

  @GetMapping("/login")
    public String login(
        @RequestParam(required=false) final Boolean loginRequired,
        @RequestParam(required=false) final Boolean loginError,
        @RequestParam(required=false) final Boolean logoutSuccess,
        final Model model) {
        
        // Si accès non authentifié à une page protégée
        if (loginRequired == Boolean.TRUE) {
            model.addAttribute("errorMessage", "Vous devez vous connecter pour avoir accès.");
        }
        
        // Si login échoué
        if (loginError == Boolean.TRUE) {
            model.addAttribute("errorMessage", "Échec de la connexion !");
        }
        
        //  Si déconnexion réussie
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute("successMessage", "Vous êtes déconnecté avec succès.");
        }

        return "authentication/login";
    }
}
