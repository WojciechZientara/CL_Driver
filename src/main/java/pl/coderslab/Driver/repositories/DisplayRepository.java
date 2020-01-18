package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Driver.entities.Display;
import pl.coderslab.Driver.entities.User;

public interface DisplayRepository extends JpaRepository<Display, Long> {

}
