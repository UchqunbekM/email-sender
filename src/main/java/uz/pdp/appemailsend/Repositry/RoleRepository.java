package uz.pdp.appemailsend.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appemailsend.Entity.Enums.Rolenames;
import uz.pdp.appemailsend.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
 Role findByRolename(Rolenames rolename);
}
