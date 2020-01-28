package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.driver.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findUserById(Long userId);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = ?1")
    User findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_roles WHERE user_id = ?1", nativeQuery = true)
    void clearUserRoleAssociations(Long userId);

}
