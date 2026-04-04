package com.devfrank.hotelmanager.security.service;

import com.devfrank.hotelmanager.users.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record UserDetailsImpl(AppUser user) implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String UNDERSCORE = "_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == null) {
            return Collections.emptyList();
        }

        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().getName().toUpperCase());

        List<SimpleGrantedAuthority> permissionAuthorities = user.getRole().getPermissions() != null
                ? user.getRole().getPermissions().stream()
                  .map(p -> new SimpleGrantedAuthority((p.getModule() + UNDERSCORE + p.getAction()).toUpperCase()))
                  .toList()
                : Collections.emptyList();

        return Stream.concat(Stream.of(roleAuthority), permissionAuthorities.stream())
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive() && user.getRole().getIsActive();
    }
}