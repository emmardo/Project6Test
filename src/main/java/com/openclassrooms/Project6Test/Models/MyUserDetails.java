package com.openclassrooms.Project6Test.Models;

import com.openclassrooms.Project6Test.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private String userEmail;
    private String password;
    private List<GrantedAuthority> authorities;

    @Autowired
    private AccountRepository accountRepository;

    public MyUserDetails(User user) {

        this.userEmail = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {

        boolean status = true;

        if(accountRepository.findAccountByUserEmail(userEmail).getAccountStatus().equals("Inactive")) {

            status = false;
        }
        return status;
    }

    @Override
    public boolean isAccountNonLocked() {

        boolean status = true;

        if(accountRepository.findAccountByUserEmail(userEmail).getAccountStatus().equals("Deactivated")) {

            status = false;
        }
        return status;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        boolean status = true;

        if(accountRepository.findAccountByUserEmail(userEmail).getAccountStatus().equals("Inactive")) {

            status = false;
        }
        return status;
    }

    @Override
    public boolean isEnabled() {

        boolean status = true;

        if(accountRepository.findAccountByUserEmail(userEmail).getAccountStatus().equals("NotYetActivated")) {

            status = false;
        }
        return status;
    }
}
