package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.MyUserDetails;
import com.openclassrooms.Project6Test.Models.Role;
import com.openclassrooms.Project6Test.Models.User;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyUserDetailsTest {

    private User user = new User();

    private Role role = new Role();

    private GrantedAuthority grantedAuthority;

    private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    @Test
    public void parameterizedConstructor() {

        //Act
        role.setRole("Regular");

        user.setEmail("abc@abc.com");
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals(user.getEmail(), myUserDetails.getUsername());
    }

    @Test
    public void setAuthorities() {

        //Act
        role.setRole("Regular");

        user.setPassword("123456");
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals("Regular", myUserDetails.getAuthorities().stream().findFirst().get().getAuthority().toString());
    }

    @Test
    public void setPassword() {

        //Act
        role.setRole("Regular");

        user.setPassword("123456");
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals("123456", myUserDetails.getPassword());
    }

    @Test
    public void setUsername() {

        //Act
        role.setRole("Regular");

        user.setEmail("abc@abc.com");
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals("abc@abc.com", myUserDetails.getUsername());
    }

    @Test
    public void setIsAccountNonExpired() {

        //Act
        role.setRole("Regular");

        user.setActive(true);
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals(true, myUserDetails.isAccountNonExpired());
    }

    @Test
    public void setIsAccountNonLocked() {

        //Act
        role.setRole("Regular");

        user.setActive(true);
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals(true, myUserDetails.isAccountNonLocked());
    }

    @Test
    public void setIsCredentialsNonExpired() {

        //Act
        role.setRole("Regular");

        user.setActive(true);
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals(true, myUserDetails.isCredentialsNonExpired());
    }

    @Test
    public void setIsEnabled() {

        //Act
        role.setRole("Regular");

        user.setActive(true);
        MyUserDetails myUserDetails = new MyUserDetails(user, role);

        //Assert
        assertEquals(true, myUserDetails.isEnabled());
    }
}
