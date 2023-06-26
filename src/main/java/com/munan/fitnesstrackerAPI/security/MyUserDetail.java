/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.security;

import com.munan.fitnesstrackerAPI.model.AppUser;
import static java.util.Arrays.stream;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author godwi
 */
public class MyUserDetail implements UserDetails{

    private final AppUser user;

    public MyUserDetail(AppUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authorities = user.getRole().getAuthorities().split(",");
        return stream(authorities).map(SimpleGrantedAuthority::new).toList();
//          return List.of(new SimpleGrantedAuthority(user.getRole().getAuthorities()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }
    
}
