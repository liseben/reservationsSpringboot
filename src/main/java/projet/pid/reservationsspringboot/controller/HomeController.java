package projet.pid.reservationsspringboot.controller;

//import org.springframework.web.bind.annotation.RestController;

//import javax.annotation.processing.Generated;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("message", "Bienvenue dans votre application");
    return "home";
  }
}
