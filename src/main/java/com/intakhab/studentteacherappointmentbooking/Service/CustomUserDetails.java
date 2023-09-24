package com.intakhab.studentteacherappointmentbooking.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String fullname;
    private String className;
    private String email;
    private String username;
    private String mobile;
    private String password;
    private String gender;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String fullname, String className,
                             String email, String username,
                             String mobile, String password,
                             String gender,
                             Collection<? extends GrantedAuthority> authorities)
    {
        this.fullname = fullname;
        this.className = className;
        this.email = email;
        this.username = username;
        this.mobile = mobile;
        this.password = password;
        this.gender = gender;
        this.authorities = authorities;
    }

    public String getFullname() {
        return fullname;
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
        return username;
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
}
