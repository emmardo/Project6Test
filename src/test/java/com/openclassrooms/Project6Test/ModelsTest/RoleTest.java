package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.Role;
import com.openclassrooms.Project6Test.Models.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoleTest {

    private int id = 1;

    private String role = "";

    private List<User> users = new ArrayList<>();

    private User user = new User();

    @Test
    public void parameterizedConstructor() {

        //Act
        Role newRole = new Role(role);

        //Assert
        assertEquals(role, newRole.getRole());
    }

    @Test
    public void setId() {

        //Arrange
        Role newRole = new Role();

        //Act
        newRole.setId(id);

        //Assert
        assertEquals(id, newRole.getId());
    }

    @Test
    public void setRole() {

        //Arrange
        Role newRole = new Role();

        //Act
        newRole.setRole(role);

        //Assert
        assertEquals(role, newRole.getRole());
    }

    @Test
    public void setUsers() {

        //Arrange
        Role newRole = new Role();

        users.add(user);

        //Act
        newRole.setUsers(users);

        //Assert
        assertEquals(users, newRole.getUsers());
    }
}
