package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.Role;
import com.openclassrooms.Project6Test.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;


    @Autowired
    public RoleService(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    public void createRoles(List<String> rolesList) {

        roleRepository.deleteAll();

        for (String role: rolesList) {

            Role roleInstance = new Role(role);

            roleRepository.save(roleInstance);
        }
    }

    /*public void addRole(String role) {

        Role roleInstance = new Role(role);

        roleRepository.save(roleInstance);
    }*/

    public List<String> getAllRoles() {

        List<String> roles = null;

        if(!roleRepository.findAll().isEmpty()) {

            for (Role role : roleRepository.findAll()) {

                roles.add(role.getRole());
            }
        }

        return roles;
    }

    /*public String getRoleById(int roleId) {

        return roleRepository.findRoleById(roleId).getRole();
    }*/

    /*public void deleteRole(String role) {

        roleRepository.delete(roleRepository.findRoleByRole(role));
    }*/
}
