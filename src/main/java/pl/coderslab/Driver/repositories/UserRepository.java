package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Driver.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findUserById(Long userId);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = ?1")
    User findUserByEmail(String email);

}
