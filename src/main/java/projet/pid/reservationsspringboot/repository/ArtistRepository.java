package projet.pid.reservationsspringboot.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import projet.pid.reservationsspringboot.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {

    List<Artist> findByLastname(String lastname);

    Artist findById(int id);
}
