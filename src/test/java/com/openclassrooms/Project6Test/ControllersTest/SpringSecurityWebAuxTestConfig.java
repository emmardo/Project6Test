package com.openclassrooms.Project6Test.ControllersTest;

import com.openclassrooms.Project6Test.Models.MyUserDetails;
import com.openclassrooms.Project6Test.Models.Role;
import com.openclassrooms.Project6Test.Models.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User regular = new User("regular@paymybuddy.com", "password", new Role("Regular"));
        regular.setActive(true);
        Role regularRole = new Role("Regular");
        MyUserDetails regularUser = new MyUserDetails(regular, regularRole);

        User admin = new User("admin@paymybuddy.com", "password", new Role("Admin"));
        admin.setActive(true);
        Role adminRole = new Role("Admin");
        MyUserDetails adminUser = new MyUserDetails(admin, adminRole);

        User company = new User("company@paymybuddy.com", "password", new Role("Company"));
        company.setActive(true);
        Role companyRole = new Role("Company");
        MyUserDetails companyUser = new MyUserDetails(company, companyRole);

        return new InMemoryUserDetailsManager(Arrays.asList(regularUser, adminUser, companyUser));
    }
}
