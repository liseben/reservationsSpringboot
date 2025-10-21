// Rôle : Active le système de layout hiérarchique pour Thymeleaf 

package projet.pid.reservationsspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ThymeleafConfiguration {

  @Bean
  public LayoutDialect thymeleafDialect() {
    return new LayoutDialect();
  }
}
