package projet.pid.reservationsspringboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projet.pid.reservationsspringboot.model.User;
import projet.pid.reservationsspringboot.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 🔍 Récupérer l'utilisateur par son login
    final User user = userRepository.findByLogin(username);

    // Si l'utilisateur n'existe pas
    if (user == null) {
      throw new UsernameNotFoundException("User " + username + " not found");
    }

    // ✅ Créer un objet UserDetails avec ses droits
    return new org.springframework.security.core.userdetails.User(
        username, // Login
        user.getPassword(), // Mot de passe chiffré
        getGrantedAuthorities(user.getRole().toString()) // Rôles/permissions
    );
  }

  // Convertir les rôles en autorités Spring Security
  private List<GrantedAuthority> getGrantedAuthorities(String role) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
    return authorities;
  }

}
