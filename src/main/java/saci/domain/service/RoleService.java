package saci.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saci.domain.model.Role;
import saci.domain.service.exceptions.AlreadyExistsException;
import saci.domain.service.exceptions.NotFoundException;
import saci.infrastructure.RoleRepository;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        Optional<Role> optionalRole = roleRepository.findByName(role.getName());
        if (optionalRole.isPresent()) {
            String errorMessage = "Role name already exists: " + role.getName();
            throw new AlreadyExistsException(errorMessage);
        }
        return roleRepository.save(role);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role editRole(Long roleId, Role updatedRole) {
        Role existingRole =
                roleRepository
                        .findById(roleId)
                        .orElseThrow(
                                () -> {
                                    String errorMessage = "Role not found";
                                    log.error(errorMessage);
                                    return new NotFoundException(errorMessage);
                                });

        existingRole.setName(updatedRole.getName());
        return roleRepository.save(existingRole);
    }

    public void deleteRoleById(long roleId) {
        roleRepository.deleteById(roleId);
    }

    public Role getRoleById(Long roleId) {
        return roleRepository
                .findById(roleId)
                .orElseThrow(
                        () -> {
                            String errorMessage = "Role not found";
                            return new NotFoundException(errorMessage);
                        });
    }
}
