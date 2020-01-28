package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.driver.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleById(Long roleId);
    Role findRoleByName(String roleName);

}
