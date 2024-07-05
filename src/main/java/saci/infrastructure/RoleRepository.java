package saci.infrastructure;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import saci.domain.model.Role;

public interface RoleRepository extends ListCrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
