package org.ironhack.coursequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ironhack.coursequest.model.Role;
import org.ironhack.coursequest.model.User;
import org.ironhack.coursequest.repository.RoleRepository;
import org.ironhack.coursequest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor // para crear el constructor que usamos para la inyección de dependencias
public class RoleService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    /**
     * Saves a new role to the database
     *
     * @param role the role to be saved
     * @return the saved role
     */
    public Role save(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        // todo roleName make it UNIQUE
        return roleRepository.save(role);
    }

    /**
     * Adds a role to the user with the given username
     *
     * @param username the username of the user to add the role to
     * @param roleName the name of the role to be added
     */
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);

        // Retrieve the user and role objects from the repository
        User user = userRepository.findByUsername(username); // todo throw exception if not found
        Role role = roleRepository.findByName(roleName); // todo throw exception if not found

        // Add the role to the user's role collection
        List<Role> userRoles = user.getRoles();
        // user.setRoles(List.of(role)); --> MAL, 1º el setRoles directamente se carga todos los roles previos de ese usuario, 2º el List.of me va a crear una lista inmutable, no modificable
        userRoles.add(role);

        // Save the user to persist the changes
        userRepository.save(user);
    }
}
