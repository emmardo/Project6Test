package com.openclassrooms.Project6Test.Repositories;


import com.openclassrooms.Project6Test.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByRole(String role);
}
