package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.MyUserDetails;
import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(userEmail);

        Optional.of(user).orElseThrow(() -> new UsernameNotFoundException("Not found: " + userEmail));

        return new MyUserDetails(user);
    }
}
