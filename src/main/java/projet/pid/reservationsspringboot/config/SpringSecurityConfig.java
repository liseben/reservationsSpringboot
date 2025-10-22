package projet.pid.reservationsspringboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

  @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http
            // Activer CORS (Cross-Origin Resource Sharing)
            .cors(Customizer.withDefaults())
            
            // Activer CSRF (protection contre les attaques CSRF)
            .csrf(Customizer.withDefaults())
            
            // Définir les autorisations d'accès
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/").permitAll();              // Homepage accessible à tous
                auth.requestMatchers("/login").permitAll();        // Formulaire de login accessible à tous
                auth.requestMatchers("/artists").authenticated();  // Artists page : authentifié requis
                auth.requestMatchers("/admin").hasRole("ADMIN");   // Admin page : ADMIN requis
                auth.anyRequest().authenticated();                 // Tout le reste : authentifié
            })
            
            // Configurer le formulaire de connexion
            .formLogin(form -> form
                .loginPage("/login")                              // URL du formulaire de login
                .usernameParameter("login")                       // Paramètre pour le username
                .passwordParameter("password")                    // Paramètre pour le password
                .defaultSuccessUrl("/", true)                     // Redirection après succès
                .failureUrl("/login?loginError=true"))            // Redirection après échec
            
            // Configurer la déconnexion
            .logout(logout -> logout
                .logoutUrl("/logout")                             // URL pour se déconnecter
                .logoutSuccessUrl("/login?logoutSuccess=true")    // Redirection après déconnexion
                .deleteCookies("JSESSIONID"))                      // Supprimer les cookies
            
            // Configurer les exceptions
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
            
            .build();
    }
}
