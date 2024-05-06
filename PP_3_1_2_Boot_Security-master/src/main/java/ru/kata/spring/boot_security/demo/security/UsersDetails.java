package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UsersDetails implements UserDetails {

    private User user;

    public UsersDetails(User user){
        this.user=user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> set = user.getRole();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : set) {
            grantedAuthorities.add(new RoleGrantedAuthority(role));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //нужно чтобы получать данные аутентифицированного пользователя
    public User getUser(){
        return this.user;
    }

}
