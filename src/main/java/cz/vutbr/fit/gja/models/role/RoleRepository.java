package cz.vutbr.fit.gja.models.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class encapsulates all methods for the Role entity that communicates with the database
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Find Role by user role
     * @param role role of user
     * @return Role if exist, null otherwise
     */
    Role findByRoleName(String role);
}
