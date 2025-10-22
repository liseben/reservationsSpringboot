package projet.pid.reservationsspringboot.controller;

//import org.springframework.web.bind.annotation.RestController;

//import javax.annotation.processing.Generated;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index() {
        return "home";  // Rend le template home.html
  }
}
