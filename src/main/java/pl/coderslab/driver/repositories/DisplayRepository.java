package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.driver.entities.Display;

public interface DisplayRepository extends JpaRepository<Display, Long> {

}
