package com.openclassrooms.Project6Test.ServicesTest;

import com.openclassrooms.Project6Test.Models.Role;
import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Repositories.UserRepository;
import com.openclassrooms.Project6Test.Services.MyUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Test
    public void loadUserByUsername_userExists_userReturned() {

        //Arrange
        String roleString = "Regular";
        Role role = new Role();
        String userEmail = "abc@abc.com";
        User user = new User();
        role.setRole(roleString);
        user.setEmail(userEmail);
        user.setRole(role);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(user);

        //Act
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

        //Assert
        assertEquals(userDetails.getUsername(), userEmail);
    }

    @Test
    public void loadUserByUsername_userInexistent_exceptionThrown() {

        //Arrange
        String userEmail = "abc@abc.com";

        when(userRepository.findUserByEmail(userEmail)).thenReturn(null);

        //Act
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

        //Assert
        assertNull(userDetails);
    }
}
