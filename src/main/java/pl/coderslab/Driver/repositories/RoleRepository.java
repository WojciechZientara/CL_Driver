package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Driver.entities.Role;
import pl.coderslab.Driver.entities.User;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleById(Long roleId);
    Role findRoleByName(String roleName);

}
